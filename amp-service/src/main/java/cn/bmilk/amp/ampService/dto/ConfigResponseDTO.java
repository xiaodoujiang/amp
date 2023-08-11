package cn.bmilk.amp.ampService.dto;

import lombok.Data;

@Data
public class ConfigResponseDTO {

    /**
     * 配置项key
     */
    private String configKey;
    /**
     * 配置项值
     */
    private String configValue;

    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 配置项备注
     */
    private String configDesc;

    /**
     * 更新类型
     */
    private String updateType;
}
