package cn.bmilk.amp.nacosgw.controller;

import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.amp.gwcommon.response.ResponseCodeEnum;
import cn.bmilk.amp.nacosgw.service.DistributeService;
import cn.bmilk.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/gw")
public class GwRequestController {
    @Resource
    private DistributeService distributeService;

    @PostMapping("/distribute")
    public String distribute(@RequestBody GwRequestDTO gwRequestDTO){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        try {
            String verify = verify(gwRequestDTO);
            if(StringUtils.isNoneBlank(verify)){
                responseDTO.setStatus(StatusEnum.FAILURE.name());
                responseDTO.setErrCode(ResponseCodeEnum.PARAMS_ERROR.getCode());
                responseDTO.setErrMsg(verify);
                return GsonUtils.toJson(responseDTO);
            }
            responseDTO = distributeService.distribute(gwRequestDTO);
        }catch (IllegalArgumentException illegalArgumentException){
            log.error("gw-distribute failure, gwRequestDTO[{}], errMsg[{}]", gwRequestDTO, illegalArgumentException.getMessage(), illegalArgumentException);
            responseDTO.setStatus(StatusEnum.FAILURE.name());
            responseDTO.setErrCode(ResponseCodeEnum.PARAMS_ERROR.getCode());
            responseDTO.setErrMsg(illegalArgumentException.getMessage());
        }catch (Exception e){
            log.error("gw-distribute failure, gwRequestDTO[{}], errMsg[{}]", gwRequestDTO, e.getMessage(), e);
            responseDTO.setStatus(StatusEnum.FAILURE.name());
            responseDTO.setErrCode(ResponseCodeEnum.SYSTEM_ERROR.getCode());
            responseDTO.setErrMsg(e.getMessage());
        }finally {
            log.info("gw-distribute end, gwRequestDTO[{}], response[{}]", gwRequestDTO, responseDTO);
        }
        return GsonUtils.toJson(responseDTO);

    }

    private String verify(GwRequestDTO gwRequestDTO){
        StringBuilder sb = new StringBuilder();

        if(StringUtils.isBlank(gwRequestDTO.getChannel())){
            sb.append("channel can not null;");
        }
        if(StringUtils.isBlank(gwRequestDTO.getBusinessType())){
            sb.append("businessType can not null;");
        }
        if(StringUtils.isBlank(gwRequestDTO.getRequestDataJson())){
            sb.append("requestData can not null;");
        }
        return sb.toString();
    }
}
