package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpEnvironmentEntity extends BaseEntity {
    /**
     * 环境名称
     */
    private String environmentName;
    /**
     * 环境描述
     */
    private String environmentDesc;
}
