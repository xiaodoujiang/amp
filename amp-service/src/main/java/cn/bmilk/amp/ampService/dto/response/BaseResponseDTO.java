package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.common.StatusEnum;
import cn.bmilk.amp.gwcommon.response.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseDTO implements Serializable {

    private String status;

    private String errCode;

    private String errMsg;

    private Object data;

    public static BaseResponseDTO SUCCESS(){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setStatus(StatusEnum.SUCCESS.name());
        baseResponseDTO.setStatus(ResponseCodeEnum.SUCCESS.getCode());
        baseResponseDTO.setStatus(ResponseCodeEnum.SUCCESS.getMsg());
        return baseResponseDTO;
    }
    public static BaseResponseDTO SUCCESS(Object date){
        BaseResponseDTO baseResponseDTO = SUCCESS();
        baseResponseDTO.setData(date);
        return baseResponseDTO;
    }

    public static BaseResponseDTO FAILURE(){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
        baseResponseDTO.setStatus(ResponseCodeEnum.SYSTEM_ERROR.getCode());
        baseResponseDTO.setStatus(ResponseCodeEnum.SYSTEM_ERROR.getMsg());
        return baseResponseDTO;
    }
    public static BaseResponseDTO FAILURE(ResponseCodeEnum responseCodeEnum){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
        baseResponseDTO.setStatus(responseCodeEnum.getCode());
        baseResponseDTO.setStatus(responseCodeEnum.getMsg());
        return baseResponseDTO;
    }

    public static BaseResponseDTO FAILURE(ResponseCodeEnum responseCodeEnum, String errMsg){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
        baseResponseDTO.setStatus(responseCodeEnum.getCode());
        baseResponseDTO.setStatus(errMsg);
        return baseResponseDTO;
    }
}
