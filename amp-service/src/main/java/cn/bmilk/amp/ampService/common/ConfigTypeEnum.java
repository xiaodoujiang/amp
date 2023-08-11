package cn.bmilk.amp.ampService.common;


public enum ConfigTypeEnum {

    APP_DEPENDENCY("应用依赖"),
    DB_DEPENDENCY("数据库依赖"),
    NORMAL_CONFIG("普通配置项");

    private String desc;

    ConfigTypeEnum(String desc) {
        this.desc = desc;
    }
}
