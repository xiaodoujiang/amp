package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.dto.response.ApplicationResponseDTO;
import cn.bmilk.amp.ampService.dto.response.BaseResponseDTO;
import cn.bmilk.amp.ampService.service.AppService;
import cn.bmilk.amp.gwcommon.response.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/create")
    public BaseResponseDTO createApp(@RequestBody ApplicationRequestDTO requestDTO){
        log.info("AppController createApp start, requestDTO[{}]", requestDTO);
        BaseResponseDTO responseDTO = null;
        try {
            String appVerify = createAppVerify(requestDTO);
            if(StringUtils.isNotBlank(appVerify)){
                responseDTO = BaseResponseDTO.FAILURE(ResponseCodeEnum.PARAMS_ERROR, appVerify);
                return responseDTO;
            }
            responseDTO =  BaseResponseDTO.SUCCESS(appService.createApp(requestDTO));
        }catch (Exception e){
            log.error("AppController createApp error, requestDTO[{}], errMsg[{}]", requestDTO, e.getMessage(), e);
            responseDTO =  BaseResponseDTO.FAILURE(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }finally {
            log.info("AppController createApp end, requestDTO[{}], responseDTO[{}]", requestDTO, responseDTO);
        }
        return responseDTO;
    }

    @PostMapping("/colony/bind")
    public BaseResponseDTO bindAppColony(@RequestBody List<AppColonyBindDTO> appColonyBindDTOList) {
        try {
            appService.bindAppColony(appColonyBindDTOList);
            return BaseResponseDTO.SUCCESS();
        }catch (Exception e){
            log.error("AppController bindAppColony exception, requestDTO[{}], errMsg[{}]", appColonyBindDTOList, e.getMessage(), e);
            return BaseResponseDTO.FAILURE(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }
    }

    @GetMapping("/config/list")
    public BaseResponseDTO queryAppConfigList(@RequestParam(name = "appName") String appName,
                                              @RequestParam(name = "env") String env,
                                              @RequestParam(name = "colonyName") String colonyName){

        log.info("AppController queryAppConfigList start, appName[{}], env[{}], colonyName[{}]", appName, env, colonyName);
        BaseResponseDTO responseDTO = null;
        try {
            responseDTO =  BaseResponseDTO.SUCCESS(appService.queryAppConfigList(appName, env, colonyName));
        }catch (Exception e){
            log.info("AppController queryAppConfigList error, appName[{}], env[{}], colonyName[{}], errMsg[{}]",
                    appName, env, colonyName, e.getMessage(), e);
            responseDTO =  BaseResponseDTO.FAILURE(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }finally {
            log.info("AppController queryAppConfigList end, appName[{}], env[{}], colonyName[{}], responseDTO[{}]",
                    appName, env, colonyName, responseDTO);
        }
        return responseDTO;

    }



    private String createAppVerify(ApplicationRequestDTO requestDTO){

        StringBuilder sb = new StringBuilder();


        if(StringUtils.isBlank(requestDTO.getAppName())){
            sb.append("appName must not null;");
        }
        if(StringUtils.isBlank(requestDTO.getAppDesc())){
            sb.append("appDesc must not null;");
        }
        if(StringUtils.isBlank(requestDTO.getAppType())){
            sb.append("appType must not null;");
        }
        if(StringUtils.isBlank(requestDTO.getOperator())){
            sb.append("operator must not null;");
        }
        return sb.toString();
    }


}
