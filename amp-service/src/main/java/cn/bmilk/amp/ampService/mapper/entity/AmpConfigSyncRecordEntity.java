package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.common.CommonStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpConfigSyncRecordEntity extends BaseEntity{
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 环境
     */
    private String environment;
    /**
     * 集群名称
     */
    private String colonyName;
    /**
     * 状态
     */
    private String status;

    public static AmpConfigSyncRecordEntity build(String appName, String colonyName, String env){
        AmpConfigSyncRecordEntity result = new AmpConfigSyncRecordEntity();

        result.setAppName(appName);
        result.setColonyName(colonyName);
        result.setEnvironment(env);
        result.setStatus(CommonStatusEnum.NEW.name());
        return result;
    }
}
