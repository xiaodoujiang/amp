package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpConfigItemEntity extends BaseEntity {
    /**
     * 应用id
     */
    private long appId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 配置项key
     */
    private String configKey;
    /**
     * 配置项value
     */
    private String configValue;
    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 描述
     */
    private String configDesc;
    /**
     * 环境
     */
    private String environment;
}
