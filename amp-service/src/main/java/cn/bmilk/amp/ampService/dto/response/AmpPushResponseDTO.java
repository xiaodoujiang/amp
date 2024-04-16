package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.dto.ConfigPushDetailDTO;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import lombok.Data;

import java.util.List;

@Data
public class AmpPushResponseDTO {
    /**
     * amp单号
     */
    private String ampNo;

    /**
     * 环境
     */
    private String environment;

    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 项目描述
     */
    private String ampDesc;

    /**
     * amp单状态
     * @see cn.bmilk.amp.ampService.common.AmpStatusEnum
     */
    private String ampStatus;
    /**
     * 推送详情列表
     */
    private List<ConfigPushDetailDTO> configPushDetailDTOList;

    public static AmpPushResponseDTO build(AmpRecordEntity ampRecordEntity){
        AmpPushResponseDTO result = new AmpPushResponseDTO();
        result.setAmpNo(ampRecordEntity.getAmpNo());
        result.setAmpStatus(ampRecordEntity.getStatus());
        result.setEnvironment(ampRecordEntity.getEnvironment());
        result.setApplicationName(ampRecordEntity.getApplicationName());
        return  result;
    }
}
