package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.AppColonyConfigDTO;
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
     * 配置项描述
     */
    private String configDesc;
    /**
     * 应用名称
     */
    private String appName;

    public static AmpColonyConfigEntity build(AppColonyConfigDTO appColonyConfigDTO, AppColonyBindDTO appColonyBindDTO){

        AmpColonyConfigEntity result = new AmpColonyConfigEntity();

        result.setColonyName(appColonyBindDTO.getColonyName());
        result.setAppName(appColonyBindDTO.getAppName());
        result.setConfigKey(appColonyConfigDTO.getConfigKey());
        result.setConfigValue(appColonyConfigDTO.getConfigValue());
        result.setConfigDesc(appColonyConfigDTO.getConfigDesc());
        return result;
    }
}
