package cn.bmilk.amp.ampService.common;

public enum AppTypeEnum {
    BUSINESS_APP("业务应用"),
    BASIC_COMPONENTS("基础组件"),
    ;

    private String desc;

    AppTypeEnum(String desc) {
        this.desc = desc;
    }
}
