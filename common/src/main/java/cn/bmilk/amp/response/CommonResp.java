package cn.bmilk.amp.response;

import lombok.Data;

@Data
public class CommonResp<T> {

    private String errorCode;

    private String errorMsg;

    private T data;


    public CommonResp(ResponseCodeEnum responseCodeEnum, String msg) {
        this(responseCodeEnum.getCode(), msg, null);
    }
    public CommonResp(ResponseCodeEnum responseCodeEnum, T data) {
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), data);
    }
    public CommonResp(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), null);
    }

    public CommonResp(String code, String msg, T data) {
        this.errorCode = code;
        this.errorMsg = msg;
        this.data = data;
    }

    public static <T> CommonResp<T> SUCCESS(T data){
        return new CommonResp<T>(ResponseCodeEnum.SUCCESS, data);
    }
    public static <T> CommonResp<T> FAILURE(){
        return new CommonResp<T>(ResponseCodeEnum.SYSTEM_ERROR);
    }

    public static <T> CommonResp<T> FAILURE(ResponseCodeEnum responseCodeEnum, String msg){
        return new CommonResp<T>(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
