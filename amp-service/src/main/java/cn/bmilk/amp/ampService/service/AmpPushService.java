package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;

public interface AmpPushService {

    boolean push(AmpPushRecordEntity ampPushRecordEntity,
                 AmpApplicationEntity ampApplicationEntity);

}
