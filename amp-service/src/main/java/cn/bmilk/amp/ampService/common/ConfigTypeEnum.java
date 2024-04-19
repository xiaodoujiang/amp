package cn.bmilk.amp.ampService.common;


public enum ConfigTypeEnum {

    APP_DEPENDENCY("应用依赖"),
    BASIC_DEPENDENCY("基础组件依赖"), //kafka、 数据库、 redis
    NORMAL_CONFIG("普通配置项");

    private String desc;

    ConfigTypeEnum(String desc) {
        this.desc = desc;
    }
}
