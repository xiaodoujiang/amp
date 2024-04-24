package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.gwcommon.response.QueryConfigResponseDTO;

import java.util.Map;

public interface AppConfigService {

    /**
     * 推送配置项
     * @param ampPushRecordEntity
     * @param ampApplicationEntity
     * @return
     */
    boolean push(AmpPushRecordEntity ampPushRecordEntity,
                 AmpApplicationEntity ampApplicationEntity);

    /**
     * 查询配置项
     */
    Map<String, String> queryAppConfig(AmpApplicationEntity ampApplicationEntity,
                                       String colonyName,
                                       String env);

}
