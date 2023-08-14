package cn.bmilk.amp.ampService.dto;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import lombok.Data;

@Data
public class ConfigResponseDTO {

    /**
     * 配置项key
     */
    private String configKey;
    /**
     * 配置项值
     */
    private String configValue;

    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 配置项备注
     */
    private String configDesc;

    /**
     * 更新类型
     */
    private String updateType;
    /**
     * 环境名称
     */
    private String environmentName;

    public ConfigResponseDTO() {
    }

    public ConfigResponseDTO(AmpConfigItemTmpEntity ampConfigItemTmpEntity){
        this.configKey = ampConfigItemTmpEntity.getConfigKey();
        this.configValue=ampConfigItemTmpEntity.getConfigValue();
        this.configType= ampConfigItemTmpEntity.getConfigType();
        this.configDesc = ampConfigItemTmpEntity.getConfigDesc();
        this.updateType = ampConfigItemTmpEntity.getUpdateType();
        this.environmentName = ampConfigItemTmpEntity.getEnvironmentName();
    }
}
