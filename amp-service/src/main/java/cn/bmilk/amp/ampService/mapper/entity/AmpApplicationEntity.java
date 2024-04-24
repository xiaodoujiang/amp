package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.common.AppConFigFormatEnum;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpApplicationEntity extends BaseEntity {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用描述
     */
    private String appDesc;
    /**
     * 应用类型
     */
    private String appType;
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
     * 配置文件类型
     */
    private String configFileType;
    /**
     * 使用的配置中心类型
     */
    private String configCenterApp;
    /**
     * 配置分组
     */
    private String configGroup;
    /**
     * 配置中心租户
     */
    private String configTenant;
    /**
     * 操作员
     */
    private String operator;

    public static AmpApplicationEntity build(ApplicationRequestDTO requestDTO){
        AmpApplicationEntity result = new AmpApplicationEntity();
        String appName = requestDTO.getAppName();
        result.appName = appName;
        result.appDesc = requestDTO.getAppDesc();
        result.appType = requestDTO.getAppType();
        result.addressEnvKey = AppConFigFormatEnum.getRequestAddressKey(appName);
        result.addressEnvValue = AppConFigFormatEnum.getRequestAddressValue(appName);
        result.pathEnvKey = AppConFigFormatEnum.getRequestPathKey(appName);
        result.pathEnvValue = AppConFigFormatEnum.getRequestPathValue(appName);
        result.configFileType = requestDTO.getConfigFileType();
        result.configCenterApp = requestDTO.getConfigCenterApp();
        result.configGroup = requestDTO.getConfigGroup();
        result.configTenant = requestDTO.getConfigTenant();
        result.operator = requestDTO.getOperator();

        return result;
    }
}
