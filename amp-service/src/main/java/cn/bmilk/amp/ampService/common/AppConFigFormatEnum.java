package cn.bmilk.amp.ampService.common;

public enum AppConFigFormatEnum {

    REQUEST_ADDRESS_ENV_KEY("%s.request.address", "请求地址配置项key"),
    REQUEST_ADDRESS_ENV_VALUE("[%s_REQUEST_ADDRESS]", "请求地址配置项value"),
    REQUEST_PATH_ENV_KEY("%s.request.path", "请求路径配置项key"),
    REQUEST_PATHS_ENV_VALUE("[%s_REQUEST_PATH]", "请求路径配置项value"),
    ;

    private String value;

    private String desc;

    AppConFigFormatEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public static String getRequestAddressKey(String appName){
        return String.format(REQUEST_ADDRESS_ENV_KEY.value, appName);
    }
    public static String getRequestAddressValue(String appName){
        return String.format(REQUEST_ADDRESS_ENV_VALUE.value, appName.toUpperCase());
    }

    public static String getRequestPathKey(String appName){
        return String.format(REQUEST_PATH_ENV_KEY.value, appName);
    }
    public static String getRequestPathValue(String appName){
        return String.format(REQUEST_PATHS_ENV_VALUE.value, appName.toUpperCase());
    }
}
