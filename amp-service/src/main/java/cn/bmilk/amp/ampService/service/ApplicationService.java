package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApplicationService {

    @Resource
    private AmpApplicationMapper applicationMapper;


    public int createApplication(ApplicationRequestDTO applicationRequestDTO){
        return 1;
    }


}
