package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpColonyEntity extends BaseEntity {
    /**
     * 集群名称名称
     */
    private String colonyName;
    /**
     * 应用描述
     */
    private String colonyDesc;
    /**
     * 所属环境Id
     */
    private long environmentId;
}
