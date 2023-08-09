package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpConfigItemTmpEntity extends BaseEntity {
    /**
     * amp单号
     */
    private String ampNo;
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
     * 变更类型
     */
    private String updateType;
    /**
     * 状态
     */
    private String status;
}
