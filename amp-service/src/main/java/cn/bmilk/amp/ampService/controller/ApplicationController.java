package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.dto.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("/application")
@Controller
public class ApplicationController {

    @Resource
    public ApplicationService applicationService;

    @PostMapping("/create")
    public int createApplication(@RequestBody ApplicationRequestDTO applicationDTO){
        return applicationService.createApplication(applicationDTO);
    }
}
