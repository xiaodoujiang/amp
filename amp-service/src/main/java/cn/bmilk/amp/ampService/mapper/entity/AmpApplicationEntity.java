package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpApplicationEntity extends BaseEntity {
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 应用描述
     */
    private String applicationDesc;
    /**
     * 应用请求地址KEY
     */
    private String addressEnvKey;
    /**
     * 应用请求地址VALUE
     */
    private String addressEnvValue;
    /**
     * 应用URI key
     */
    private String pathEnvKey;
    /**
     * 应用URI value
     */
    private String pathEnvValue;
    /**
     * 使用的配置中心类型
     */
    private String configCenterType;
    /**
     * 配置中心租户
     */
    private String configTenant;
}
