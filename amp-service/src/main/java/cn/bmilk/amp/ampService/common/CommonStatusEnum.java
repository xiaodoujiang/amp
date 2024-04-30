package cn.bmilk.amp.ampService.common;

public enum CommonStatusEnum {
    NEW("新建"),
    PROCESSING("处理中"),
    SUCCESS("成功"),
    FAILURE("失败"),
    ;
    private String desc;

    CommonStatusEnum(String desc) {
        this.desc = desc;
    }
}
