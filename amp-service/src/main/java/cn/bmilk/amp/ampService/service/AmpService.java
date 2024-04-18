package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.ConfigPushDetailDTO;
import cn.bmilk.amp.ampService.dto.request.AmpPushRequestDTO;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AmpPushResponseDTO;
import cn.bmilk.amp.ampService.dto.response.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.mapper.*;
import cn.bmilk.amp.ampService.mapper.entity.*;
import cn.bmilk.amp.ampService.task.ConfigPushTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AmpService {

    @Resource
    private AmpRecordMapper ampRecordMapper;

    @Resource
    private AmpTransactionalService ampTransactionalService;

    @Resource
    private AmpConfigItemTmpMapper ampConfigItemTmpMapper;

    @Resource
    private AmpAppColonyRelMapper ampAppColonyRelMapper;

    @Resource
    private ThreadPoolTaskExecutor pushConfigExecutor;

    @Resource
    private Map<String, AmpPushService> ampPushServiceMap;

    @Resource
    private AmpPushRecordMapper ampPushRecordMapper;

    @Resource
    private AmpApplicationMapper ampApplicationMapper;

    @Resource
    private AmpColonyMapper ampColonyMapper;

    @Transactional
    public List<AmpRecordResponseDTO> createAmp(AmpRecordRequestDTO requestDTO) {

        List<AmpRecordEntity> ampRecordEntityList = new ArrayList<>();
        List<AmpConfigItemTmpEntity> ampConfigItemTmpEntityList = new ArrayList<>();
        for (String env : requestDTO.getEnvironmentList()) {
            // todo 生成amp单号
            String ampNo = "AMP_" + System.currentTimeMillis() + env;

            AmpRecordEntity ampRecordEntity = AmpRecordEntity.build(requestDTO, ampNo, env);

            ampRecordEntityList.add(ampRecordEntity);
            List<ConfigDetailDTO> configDetailDTOList = requestDTO.getEnvConfigMap().get(env);
            for (ConfigDetailDTO configDetailDTO : configDetailDTOList) {
                AmpConfigItemTmpEntity ampConfigItemTmpEntity = AmpConfigItemTmpEntity.build(configDetailDTO, ampNo);
                ampConfigItemTmpEntityList.add(ampConfigItemTmpEntity);
            }
        }
        ampTransactionalService.createAmp(ampRecordEntityList, ampConfigItemTmpEntityList);
        List<AmpRecordResponseDTO> ampRecordResponseDTOList = new ArrayList<>();
        for (AmpRecordEntity ampRecordEntity : ampRecordEntityList) {
            AmpRecordResponseDTO ampRecordResponseDTO = new AmpRecordResponseDTO();
            ampRecordResponseDTO.setAmpNo(ampRecordEntity.getAmpNo());
            ampRecordResponseDTOList.add(ampRecordResponseDTO);
        }
        return ampRecordResponseDTOList;
    }

    public AmpRecordResponseDTO queryAmpRecord(String ampNo) {
        AmpRecordEntity ampRecordEntity = ampRecordMapper.queryAmpRecord(ampNo);
        if (null == ampRecordEntity || null == ampRecordEntity.getId()) {
            return new AmpRecordResponseDTO();
        }
        List<AmpConfigItemTmpEntity> configItemList = ampConfigItemTmpMapper.queryConfigListByAmpNo(ampNo);
        AmpRecordResponseDTO ampRecordResponseDTO = buildAmpRecordResponseDTO(ampRecordEntity, configItemList);
        return ampRecordResponseDTO;
    }

    public List<AmpRecordResponseDTO> queryAmpRecordList(String createUser, int pageSize, int pageNo) {
        List<AmpRecordEntity> ampRecordEntityList = ampRecordMapper.queryAmpRecordList(createUser, (pageNo - 1) * pageSize, pageSize);
        List<AmpRecordResponseDTO> result = new ArrayList<>();
        for (AmpRecordEntity ampRecordEntity : ampRecordEntityList) {
            result.add(buildAmpRecordResponseDTO(ampRecordEntity, null));
        }
        return result;
    }

    public void deleteAmpRecord(String ampNo) {
        ampTransactionalService.deleteAmp(ampNo);
    }


    public AmpPushResponseDTO recordPush(AmpPushRequestDTO ampPushRequestDTO) {
        String ampNo = ampPushRequestDTO.getAmpNo();
        List<String> colonyList = ampPushRequestDTO.getColonyList();
        // 查询amp单详情
        AmpRecordEntity ampRecordEntity = ampRecordMapper.queryAmpRecord(ampNo);

        // 查询应用部署的集群(如果没有传推送集群列表推送全部集群)
        if (null == colonyList || colonyList.isEmpty()) {
            List<AmpColonyEntity> ampColonyEntitieList = ampColonyMapper.queryListByEnv(ampRecordEntity.getEnvironment());
            if(null == ampColonyEntitieList || ampColonyEntitieList.isEmpty()) {
                log.error("can not find push colony, ampNo[{}],appName[{}],env[{}]", ampNo, ampRecordEntity.getApplicationName(),
                        ampRecordEntity.getEnvironment());
                throw new IllegalArgumentException("cant not find push colony");
            }
            colonyList = ampColonyEntitieList.stream().map(AmpColonyEntity::getColonyName).collect(Collectors.toList());
            List<AmpAppColonyRelEntity> ampAppEnvRelEntitieList = ampAppColonyRelMapper.queryByAppNameAndColony(ampRecordEntity.getApplicationName(),
                    colonyList);
            colonyList = ampAppEnvRelEntitieList.stream().map(AmpAppColonyRelEntity::getColonyName).collect(Collectors.toList());
        }
        // 查询需要推送的配置项
        List<AmpConfigItemTmpEntity> ampConfigItemTmpEntityList = ampConfigItemTmpMapper.queryConfigListByAmpNo(ampNo);
        // 生成推送记录单
        List<AmpPushRecordEntity> ampPushRecordEntitieList = ampTransactionalService.recordPush(ampRecordEntity, colonyList, ampConfigItemTmpEntityList);
        List<ConfigPushDetailDTO> configPushDetailDTOList = new ArrayList<>();
        for (AmpPushRecordEntity ampPushRecordEntity : ampPushRecordEntitieList) {
            pushConfigExecutor.execute(new ConfigPushTask(ampPushRecordEntity.getId()));
            configPushDetailDTOList.add(ConfigPushDetailDTO.build(ampPushRecordEntity));
        }
        AmpPushResponseDTO ampPushResponseDTO = AmpPushResponseDTO.build(ampRecordEntity);
        ampPushResponseDTO.setConfigPushDetailDTOList(configPushDetailDTOList);
        return ampPushResponseDTO;
    }

    public boolean push(long recordId) {
        AmpPushRecordEntity ampPushRecordEntity = ampPushRecordMapper.queryById(recordId);
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(ampPushRecordEntity.getApplicationName());
        AmpPushService ampPushService = ampPushServiceMap.get(ampApplicationEntity.getConfigCenterApp());
        if (ampPushService == null) {
            // todo 抛出异常
            throw new RuntimeException();
        }
        return ampPushService.push(ampPushRecordEntity, ampApplicationEntity);
    }

    private AmpRecordResponseDTO buildAmpRecordResponseDTO(AmpRecordEntity ampRecordEntity,
                                                           List<AmpConfigItemTmpEntity> configItemList) {
        if (null == ampRecordEntity) return null;
        AmpRecordResponseDTO result = new AmpRecordResponseDTO();
        result.setId(ampRecordEntity.getId());
        result.setCreateDate(ampRecordEntity.getCreateTime());
        result.setAmpNo(ampRecordEntity.getAmpNo());
        result.setAmpDesc(ampRecordEntity.getAmpDesc());
        result.setAmpTaskRel(ampRecordEntity.getAmpTaskRel());
        result.setApplicationId(ampRecordEntity.getApplicationId());
        result.setApplicationName(ampRecordEntity.getApplicationName());
        result.setEnvironment(ampRecordEntity.getEnvironment());
        result.setAmpStatus(ampRecordEntity.getStatus());
        result.setAmpDesc(ampRecordEntity.getAmpDesc());
        result.setLaunchDate(ampRecordEntity.getLaunchDate());
        result.setConfigDetailDTOList(buildEnvConfigList(configItemList));
        return result;
    }

    private List<ConfigDetailDTO> buildEnvConfigList(List<AmpConfigItemTmpEntity> configItemList) {
        if (null == configItemList) return null;
        List<ConfigDetailDTO> result = new ArrayList<>();
        for (AmpConfigItemTmpEntity ampConfigItemTmpEntity : configItemList) {
            result.add(new ConfigDetailDTO(ampConfigItemTmpEntity));
        }
        return result;
    }

}
