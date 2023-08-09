package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpColonyConfigEntity extends BaseEntity {
    /**
     * 集群Id
     */
    private long colonyId;
    /**
     * 配置中心类型
     */
    private String configCenterType;
    /**
     * 配置中心地址
     */
    private String configCenterAddress;
    /**
     * 配置中心访问用户名
     */
    private String configCenterUsername;
    /**
     * 配置中心密码
     */
    private String configCenterPassword;
    /**
     * 配置中心密钥
     */
    private String configCenterAuthKey;

}
