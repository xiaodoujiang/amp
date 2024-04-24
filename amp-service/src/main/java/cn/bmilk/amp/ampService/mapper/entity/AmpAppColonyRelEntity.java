package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
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

    public static AmpAppColonyRelEntity build(AppColonyBindDTO appColonyBindDTO){
        AmpAppColonyRelEntity result = new AmpAppColonyRelEntity();

        result.setAppName(appColonyBindDTO.getAppName());
        result.setColonyName(appColonyBindDTO.getColonyName());
        return result;
    }
}
