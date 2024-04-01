package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpColonyEntity extends BaseEntity {
    /**
     * 环境名称
     */
    private String colonyName;
    /**
     * 环境描述
     */
    private String colonyDesc;
}
