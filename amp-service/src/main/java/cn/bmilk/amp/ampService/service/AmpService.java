package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpRecordMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AmpService {

    @Resource
    private AmpRecordMapper ampRecordMapper;

    @Resource
    private AmpTransactionalService ampTransactionalService;

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
}
