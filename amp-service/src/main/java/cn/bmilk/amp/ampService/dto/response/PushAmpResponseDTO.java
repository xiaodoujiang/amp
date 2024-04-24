package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.dto.ConfigPushDetailDTO;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import lombok.Data;

import java.util.List;

@Data
public class PushAmpResponseDTO {
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
    private String appName;
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

    public static PushAmpResponseDTO build(AmpRecordEntity ampRecordEntity){
        PushAmpResponseDTO result = new PushAmpResponseDTO();
        result.setAmpNo(ampRecordEntity.getAmpNo());
        result.setAmpStatus(ampRecordEntity.getStatus());
        result.setEnvironment(ampRecordEntity.getEnvironment());
        result.setAppName(ampRecordEntity.getAppName());
        return  result;
    }
}
