package cn.bmilk.amp.gwcommon.response;

public enum ResponseCodeEnum {

    SUCCESS("0000", "请求成功"),
    PARAMS_ERROR("9001", "参数错误"),
    SYSTEM_ERROR("9999", "系统错误");

    private String code;

    private String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

