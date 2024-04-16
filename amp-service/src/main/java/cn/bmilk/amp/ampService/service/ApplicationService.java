package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.response.ApplicationResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplicationService {

    @Resource
    private AmpApplicationMapper applicationMapper;


    public int createApplication(ApplicationRequestDTO applicationRequestDTO){
        return 1;
    }

    public List<ApplicationResponseDTO> queryAllApplication(){
        List<AmpApplicationEntity> ampApplicationEntities = applicationMapper.queryAllApplication();
        return ApplicationResponseDTO.buildApplicationResponseDTOList(ampApplicationEntities);
    }

}
