package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpColonyConfigEntity extends BaseEntity{
    /**
     * 环境名称
     */
    private String colonyName;
    /**
     * 配置项
     */
    private String configKey;
    /**
     * 配置项值
     */
    private String configValue;
    /**
     * 应用名称
     */
    private String appName;
}
