package cn.bmilk.amp.ampService.common;

public enum ConfigUpdateTypeEnum {
    UPDATE("更新"),
    CREATE("新增"),
    DELETE("删除");

    private String desc;

    ConfigUpdateTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
