package cn.bmilk.amp.nacosgw.controller;

import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.amp.gwcommon.response.ResponseCodeEnum;
import cn.bmilk.amp.nacosgw.service.DistributeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/gw")
public class GwRequestController {
    @Resource
    private DistributeService distributeService;

    @PostMapping("/distribute")
    public BaseResponseDTO distribute(@RequestBody GwRequestDTO gwRequestDTO){
        try {
            String verify = verify(gwRequestDTO);
            if(StringUtils.isNoneBlank(verify)){
                BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
                baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
                baseResponseDTO.setErrCode(ResponseCodeEnum.PARAMS_ERROR.getCode());
                baseResponseDTO.setErrMsg(verify);
                return baseResponseDTO;
            }
            BaseResponseDTO distribute = distributeService.distribute(gwRequestDTO);
            return distribute;
        }catch (IllegalArgumentException illegalArgumentException){
            BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
            baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
            baseResponseDTO.setErrCode(ResponseCodeEnum.PARAMS_ERROR.getCode());
            baseResponseDTO.setErrMsg(illegalArgumentException.getMessage());
            return baseResponseDTO;
        }catch (Exception e){
            BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
            baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
            baseResponseDTO.setErrCode(ResponseCodeEnum.SYSTEM_ERROR.getCode());
            baseResponseDTO.setErrMsg(e.getMessage());
            return baseResponseDTO;
        }
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
