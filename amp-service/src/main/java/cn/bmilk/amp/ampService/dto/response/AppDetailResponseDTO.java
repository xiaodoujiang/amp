package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import lombok.Data;

@Data
public class AppDetailResponseDTO {
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


    public static AppDetailResponseDTO build(AmpApplicationEntity entity){
        AppDetailResponseDTO result = new AppDetailResponseDTO();
        result.setApplicationName(entity.getApplicationName());
        result.setApplicationDesc(entity.getApplicationDesc());
        result.setAddressEnvKey(entity.getAddressEnvKey());
        result.setAddressEnvValue(entity.getAddressEnvValue());
        result.setPathEnvKey(entity.getPathEnvKey());
        result.setPathEnvValue(entity.getPathEnvValue());
        return result;
    }
}
