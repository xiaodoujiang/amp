package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpConfigItemHisEntity extends BaseEntity {

    /**
     * 配置表Id
     */
    private long AMPCONFIGITEMId;
    /**
     * 变更人
     */
    private String updateUser;
    /**
     * 变更时间
     */
    private Date updateDate;
    /**
     * 配置项key
     */
    private String configKey;
    /**
     * 配置项value
     */
    private String configValue;
    /**
     * 配置项类型
     */
    private String configType;
    /**
     * 描述
     */
    private String configDesc;
}
