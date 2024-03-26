package cn.bmilk.amp.gwcommon;

import lombok.Data;

/**
 * 配置描述类
 */
@Data
public class ConfigurationDTO {
    // 配置项key
    private String key;
    // 配置项值
    private String value;
    // 配置项描述
    private String desc;
}
