package cn.bmilk.amp.ampService.common;

public enum ApplicationTypeEnum {
    BUSINESS_APP("业务应用"),
    BASIC_COMPONENTS("基础组件"),
    ;

    private String desc;

    ApplicationTypeEnum(String desc) {
        this.desc = desc;
    }
}
