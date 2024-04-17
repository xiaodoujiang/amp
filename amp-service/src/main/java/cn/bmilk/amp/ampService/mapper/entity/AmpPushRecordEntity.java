package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpPushRecordEntity extends BaseEntity{

    /**
     * 项目编号
     */
    private String ampNo;
    /**
     * 集群名称
     */
    private String colonyName;
    /**
     * 环境名称
     */
    private String environment;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 推送状态
     */
    private String pushStatus;
    /**
     * 流水号
     */
    private String serialNo;
    /**
     * 状态码
     */
    private String errCode;
    /**
     * 状态描述
     */
    private String errMsg;
}
