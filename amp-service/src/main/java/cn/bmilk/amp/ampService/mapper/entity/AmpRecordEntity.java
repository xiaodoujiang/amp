package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpRecordEntity extends BaseEntity {
    /**
     * 项目编号
     */
    private String ampNo;
    /**
     * 环境列表，','隔开
     */
    private String environmentList;
    /**
     * 应用id
     */
    private String applicationId;
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
     * 创建人
     */
    private String createUser;
    /**
     * amp单状态
     */
    private String status;
}
