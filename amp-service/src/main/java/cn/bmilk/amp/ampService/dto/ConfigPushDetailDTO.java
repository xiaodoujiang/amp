package cn.bmilk.amp.ampService.dto;

import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import lombok.Data;

/**
 * 推送详情
 */
@Data
public class ConfigPushDetailDTO {

    private long id;
    /**
     * 集群名称
     */
    private String colonyName;
    /**
     * 推送状态
     */
    private String pushStatus;

    public static ConfigPushDetailDTO build(AmpPushRecordEntity ampPushRecordEntity){
        ConfigPushDetailDTO configPushDetailDTO = new ConfigPushDetailDTO();
        configPushDetailDTO.setId(ampPushRecordEntity.getId());
        configPushDetailDTO.setPushStatus(ampPushRecordEntity.getPushStatus());
        configPushDetailDTO.setColonyName(ampPushRecordEntity.getColonyName());
        return configPushDetailDTO;
    }
}
