package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpAppEnvRelEntity extends BaseEntity {
    /**
     * 应用ID
     */
    private long applicationId;
    /**
     * 环境Id
     */
    private long environmentId;
    /**
     * 集群Id
     */
    private long colonyId;
}
