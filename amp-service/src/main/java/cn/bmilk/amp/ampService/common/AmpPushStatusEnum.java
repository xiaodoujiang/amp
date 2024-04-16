package cn.bmilk.amp.ampService.common;

public enum AmpPushStatusEnum {
    NEW("新建"),
    PROCESSING("处理中"),
    SUCCESS("推送成功"),
    FAILURE("推送失败"),
    ;
    private String desc;

    AmpPushStatusEnum(String desc) {
        this.desc = desc;
    }
}
