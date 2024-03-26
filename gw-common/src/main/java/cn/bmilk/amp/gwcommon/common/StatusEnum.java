package cn.bmilk.amp.gwcommon.common;

public enum StatusEnum {

    SUCCESS("成功"),
    FAILURE("失败");

    private String desc;

    StatusEnum(String desc) {
        this.desc = desc;
    }
}
