package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.common.ConfigUpdateTypeEnum;
import cn.bmilk.amp.ampService.dto.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.ConfigResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemTmpMapper;
import cn.bmilk.amp.ampService.mapper.AmpRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmpTransactionalService {

    @Resource
    private AmpRecordMapper ampRecordMapper;

    @Resource
    private AmpConfigItemTmpMapper ampConfigItemTmpMapper;



    @Transactional
    public void createAmp(AmpRecordRequestDTO ampRecordRequestDTO, String ampNo){
        AmpRecordEntity ampRecordEntity = AmpRecordEntity.buildAmpRecordEntity(ampRecordRequestDTO, ampNo);
        List<AmpConfigItemTmpEntity> ampConfigItemTmpEntityList = new ArrayList<>();
        if(ampRecordRequestDTO.isMulEnvConfigConsistent()){
            for (String env : ampRecordRequestDTO.getEnvironmentList()){
                String envValue = new ArrayList<>(ampRecordRequestDTO.getEnvConfigMap().keySet()).get(0);
                for (ConfigResponseDTO configResponseDTO: ampRecordRequestDTO.getEnvConfigMap().get(envValue)){
                    AmpConfigItemTmpEntity ampConfigItemTmpEntity = new AmpConfigItemTmpEntity();
                    ampConfigItemTmpEntity.setAmpNo(ampNo);
                    ampConfigItemTmpEntity.setConfigDesc(configResponseDTO.getConfigDesc());
                    ampConfigItemTmpEntity.setConfigKey(configResponseDTO.getConfigKey());
                    ampConfigItemTmpEntity.setConfigValue(configResponseDTO.getConfigValue());
                    ampConfigItemTmpEntity.setConfigType(StringUtils.isBlank(configResponseDTO.getConfigType())?
                            ConfigTypeEnum.NORMAL_CONFIG.name() : configResponseDTO.getConfigType());
                    ampConfigItemTmpEntity.setUpdateType(StringUtils.isBlank(configResponseDTO.getUpdateType())?
                            ConfigUpdateTypeEnum.CREATE.name() : configResponseDTO.getUpdateType());
                    ampConfigItemTmpEntity.setEnvironmentName(env);
                    ampConfigItemTmpEntityList.add(ampConfigItemTmpEntity);
                }
            }
        }else {
            for (String env : ampRecordRequestDTO.getEnvConfigMap().keySet()){
                for (ConfigResponseDTO configResponseDTO: ampRecordRequestDTO.getEnvConfigMap().get(env)){
                    AmpConfigItemTmpEntity ampConfigItemTmpEntity = new AmpConfigItemTmpEntity();
                    ampConfigItemTmpEntity.setAmpNo(ampNo);
                    ampConfigItemTmpEntity.setConfigDesc(configResponseDTO.getConfigDesc());
                    ampConfigItemTmpEntity.setConfigKey(configResponseDTO.getConfigKey());
                    ampConfigItemTmpEntity.setConfigValue(configResponseDTO.getConfigValue());
                    ampConfigItemTmpEntity.setConfigType(StringUtils.isBlank(configResponseDTO.getConfigType())?
                            ConfigTypeEnum.NORMAL_CONFIG.name() : configResponseDTO.getConfigType());
                    ampConfigItemTmpEntity.setUpdateType(StringUtils.isBlank(configResponseDTO.getUpdateType())?
                            ConfigUpdateTypeEnum.CREATE.name() : configResponseDTO.getUpdateType());
                    ampConfigItemTmpEntity.setEnvironmentName(env);
                    ampConfigItemTmpEntityList.add(ampConfigItemTmpEntity);
                }
            }
        }
        ampRecordMapper.insert(ampRecordEntity);
        ampConfigItemTmpMapper.batchInsert(ampConfigItemTmpEntityList);
    }




}
