package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpAppColonyRelEntity extends BaseEntity {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 集群名称
     */
    private String colonyName;
}
