package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

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
     * 依赖应用名称
     */
    private String dependAppName;
    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 描述
     */
    private String configDesc;

    public static AmpConfigItemTmpEntity build(ConfigDetailDTO configDetailDTO, String ampNo){
        AmpConfigItemTmpEntity ampConfigItemTmpEntity = new AmpConfigItemTmpEntity();
        ampConfigItemTmpEntity.setAmpNo(ampNo);
        String configType = StringUtils.isBlank(configDetailDTO.getConfigType()) ? ConfigTypeEnum.NORMAL_CONFIG.name() : configDetailDTO.getConfigType();
        ampConfigItemTmpEntity.setConfigType(configType);
        ampConfigItemTmpEntity.setConfigKey(configDetailDTO.getConfigKey());
        ampConfigItemTmpEntity.setConfigValue(configDetailDTO.getConfigValue());
        ampConfigItemTmpEntity.setConfigDesc(configDetailDTO.getConfigValue());
        ampConfigItemTmpEntity.setDependAppName(configDetailDTO.getDependAppName());
        return ampConfigItemTmpEntity;
    }
}
