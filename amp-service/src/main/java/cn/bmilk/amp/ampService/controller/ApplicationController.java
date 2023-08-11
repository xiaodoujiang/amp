package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.dto.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.ApplicationResponseDTO;
import cn.bmilk.amp.ampService.service.ApplicationService;
import cn.bmilk.amp.response.CommonResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/application")
@RestController
public class ApplicationController {

    @Resource
    public ApplicationService applicationService;

    @PostMapping("/create")
    public int createApplication(@RequestBody ApplicationRequestDTO applicationDTO){
        return applicationService.createApplication(applicationDTO);
    }

    @GetMapping("/queryAll")
    public CommonResp<List<ApplicationResponseDTO>> queryAllApplication(){
        return CommonResp.SUCCESS(applicationService.queryAllApplication());
    }

}
