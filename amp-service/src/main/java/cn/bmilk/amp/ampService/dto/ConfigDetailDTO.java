package cn.bmilk.amp.ampService.dto;

import cn.bmilk.amp.ampService.mapper.AmpConfigItemMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConfigDetailDTO {

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

    public ConfigDetailDTO() {
    }

    public ConfigDetailDTO(AmpConfigItemTmpEntity ampConfigItemTmpEntity) {
        this.configKey = ampConfigItemTmpEntity.getConfigKey();
        this.configValue = ampConfigItemTmpEntity.getConfigValue();
        this.configType = ampConfigItemTmpEntity.getConfigType();
        this.configDesc = ampConfigItemTmpEntity.getConfigDesc();
    }

    public ConfigDetailDTO(AmpConfigItemEntity ampConfigItemEntity) {
        this.configKey = ampConfigItemEntity.getConfigKey();
        this.configValue = ampConfigItemEntity.getConfigValue();
        this.configType = ampConfigItemEntity.getConfigType();
        this.configDesc = ampConfigItemEntity.getConfigDesc();
    }

}
