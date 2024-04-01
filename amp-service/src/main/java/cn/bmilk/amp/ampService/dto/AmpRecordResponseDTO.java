package cn.bmilk.amp.ampService.dto;

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
     * 环境列表，','隔开
     */
    private List<String> environmentList;
    /**
     * 应用id
     */
    private Long applicationId;
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
    private String status;

    /**
     * 配置项列表d
     */
    private Map<String, List<ConfigResponseDTO>> envConfigMap;
}
