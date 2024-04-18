package cn.bmilk.amp.nacosgw.config;

public enum RequestURIEnum {

    LOGIN("/v1/auth/login", "登录"),
    GET_CONFIG("/v2/cs/config","获取配置"),
    PUSH_CONFIG("/v2/cs/config", "发布配置"),
    ;

    private String uri;

    private String desc;

    RequestURIEnum(String uri, String desc) {
        this.uri = uri;
        this.desc = desc;
    }

    public String getUri() {
        return uri;
    }

    public String getDesc() {
        return desc;
    }
}
