package cn.bmilk.amp.ampService.common;

public enum AmpStatusEnum {

    NEW("新建"),
    WAIT_PUSH("待推送"),
    PUSH_PROCESSING("推送中"),
    PUSH_SUCCESS("推送成功"),
    PUSH_FAILURE("推送失败"),
    DISCARD("废弃");

    private String desc;

    AmpStatusEnum(String desc) {
        this.desc = desc;
    }
}
