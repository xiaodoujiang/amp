package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.dto.response.BaseResponseDTO;
import cn.bmilk.amp.ampService.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private AppService appService;

    @GetMapping("/detail")
    public BaseResponseDTO queryAppDetail(@RequestParam(name = "appName") String appName){
        AppDetailResponseDTO appDetailResponseDTO = appService.queryAppDetail(appName);
        return BaseResponseDTO.SUCCESS(appDetailResponseDTO);
    }

//    @GetMapping("/list")
//    public BaseResponseDTO vagueQueryAppList(@RequestParam(name = "keyWord") String keyWord){
//
//    }


}
