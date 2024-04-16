package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AmpRecordResponseDTO {

    private Long id;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * amp单号
     */
    private String ampNo;

    /**
     * 环境
     */
    private String environment;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 项目描述
     */
    private String ampDesc;
    /**
     * 关联Task地址
     */
    private String ampTaskRel;
    /**
     * 预计上线日期
     */
    private Date launchDate;

    /**
     * amp单状态
     * @see cn.bmilk.amp.ampService.common.AmpStatusEnum
     */
    private String ampStatus;

    /**
     * 配置项列表d
     */
    private List<ConfigDetailDTO> configDetailDTOList;
}
