package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpConfigItemHisEntity extends BaseEntity {

    /**
     * amp单号
     */
    private String ampNo;
    /**
     * 配置表Id
     */
    private long ampConfigItemId;
    /**
     * 变更人
     */
    private String updateUser;
    /**
     * 变更时间
     */
    private Date updateDate;
    /**
     * 配置项value
     */
    private String configValue;
    /**
     * 描述
     */
    private String configDesc;
    /**
     * 变更类型
     */
    private String updateType;
}
