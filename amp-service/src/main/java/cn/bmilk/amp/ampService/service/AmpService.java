package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.dto.ConfigResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemTmpMapper;
import cn.bmilk.amp.ampService.mapper.AmpRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AmpService {

    @Resource
    private AmpRecordMapper ampRecordMapper;

    @Resource
    private AmpTransactionalService ampTransactionalService;

    @Resource
    private AmpConfigItemTmpMapper ampConfigItemTmpMapper;

    public AmpRecordResponseDTO createAmp(AmpRecordRequestDTO ampRecordRequestDTO) {
        if(ampRecordRequestDTO.isMulEnvConfigConsistent()){
            if(ampRecordRequestDTO.getEnvConfigMap().size()>1){
                throw new IllegalArgumentException("too many env config, MulEnvConfigConsistent");
            }
        }else {

            if(ampRecordRequestDTO.getEnvironmentList().size() != ampRecordRequestDTO.getEnvConfigMap().size()){
                throw new IllegalArgumentException("env and envConfig not match");
            }
            for (String env : ampRecordRequestDTO.getEnvironmentList()){
                if(!ampRecordRequestDTO.getEnvConfigMap().containsKey(env)){
                    throw new IllegalArgumentException("env and envConfig not match");
                }
            }
        }
        String ampNo = "AMP_" + System.currentTimeMillis();
        ampTransactionalService.createAmp(ampRecordRequestDTO, ampNo);
        AmpRecordResponseDTO ampRecordResponseDTO = new AmpRecordResponseDTO();
        ampRecordResponseDTO.setAmpNo(ampNo);
        return ampRecordResponseDTO;
    }

    public AmpRecordResponseDTO queryAmpRecord(String ampNo){
        AmpRecordEntity ampRecordEntity = ampRecordMapper.queryAmpRecord(ampNo);
        if(null == ampRecordEntity || null == ampRecordEntity.getId()){
            return new AmpRecordResponseDTO();
        }
        List<AmpConfigItemTmpEntity> configItemList = ampConfigItemTmpMapper.queryConfigListByAmpNo(ampNo);
        return buildAmpRecordResponseDTO(ampRecordEntity, configItemList);
    }

    private AmpRecordResponseDTO buildAmpRecordResponseDTO(AmpRecordEntity ampRecordEntity,
                                                           List<AmpConfigItemTmpEntity> configItemList){
        AmpRecordResponseDTO result = new AmpRecordResponseDTO();
        result.setAmpNo(ampRecordEntity.getAmpNo());
        result.setAmpDesc(ampRecordEntity.getAmpDesc());
        result.setAmpTaskRel(ampRecordEntity.getAmpTaskRel());
        result.setApplicationId(ampRecordEntity.getApplicationId());
        result.setEnvironmentList(Arrays.asList(ampRecordEntity.getEnvironmentList().split(",")));
        result.setStatus(ampRecordEntity.getStatus());
        result.setEnvConfigMap(buildEnvConfigMap(configItemList));
        return result;
    }

    public List<AmpRecordResponseDTO> queryAmpRecordList(String startDateStr, String endDateStr){
        return  null;

    }

    private Map<String, List<ConfigResponseDTO>> buildEnvConfigMap(List<AmpConfigItemTmpEntity> configItemList){
        Map<String, List<ConfigResponseDTO>> result = new HashMap<>();
        for (AmpConfigItemTmpEntity ampConfigItemTmpEntity : configItemList) {
            if(result.containsKey(ampConfigItemTmpEntity.getEnvironmentName())){
                result.get(ampConfigItemTmpEntity.getEnvironmentName()).add(new ConfigResponseDTO(ampConfigItemTmpEntity));
            }else {
                List<ConfigResponseDTO> configResponseDTOList = new ArrayList<>();
                configResponseDTOList.add(new ConfigResponseDTO(ampConfigItemTmpEntity));
                result.put(ampConfigItemTmpEntity.getEnvironmentName(), configResponseDTOList);
            }
        }
        return result;
    }

}
