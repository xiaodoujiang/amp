package cn.bmilk.amp.gwcommon.common;

public enum BusinessTypeEnum {

    AUTH_LOGIN("认证登录"),
    PUSH_CONFIGURATION("推送配置项"),
    QUERY_CONFIGURATION("查询配置项"),

    ;

    private String decs;

    BusinessTypeEnum(String decs) {
        this.decs = decs;
    }
}
