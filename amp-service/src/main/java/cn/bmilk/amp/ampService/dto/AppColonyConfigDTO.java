package cn.bmilk.amp.ampService.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppColonyConfigDTO implements Serializable {
    /**
     * 配置项key
     */
    private String configKey;
    /**
     * 配置项value
     */
    private String configValue;
    /**
     * 配置项描述
     */
    private String configDesc;
}
