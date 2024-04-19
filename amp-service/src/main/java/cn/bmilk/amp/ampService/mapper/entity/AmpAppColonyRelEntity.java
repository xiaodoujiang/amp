package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpAppColonyRelEntity extends BaseEntity {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 集群名称
     */
    private String colonyName;
    /**
     * 应用请求地址
     */
    private String appRequestAddressKey;
    /**
     * 应用请求path
     */
    private String appRequestPathKey;
    /**
     * 应用请求地址
     */
    private String appRequestAddressValue;
    /**
     * 应用请求path
     */
    private String appRequestPathValue;
    /**
     * 应用访问用户名
     */
    private String appUsername;
    /**
     * 应用访问密码
     */
    private String appPassword;
}
