package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.mapper.*;
import cn.bmilk.amp.ampService.mapper.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AppTransactionalService {

    @Resource
    private AmpAppColonyRelMapper ampAppColonyRelMapper;

    @Resource
    private AmpColonyConfigMapper ampColonyConfigMapper;

    @Resource
    private AmpConfigSyncRecordMapper ampConfigSyncRecordMapper;

    @Transactional
    public void bindAppColony(List<AmpAppColonyRelEntity> appColonyRelEntityList,
                              List<AmpColonyConfigEntity> colonyConfigEntityList,
                              List<AmpConfigSyncRecordEntity> configSyncRecordList) {
        ampAppColonyRelMapper.batchInsert(appColonyRelEntityList);
        ampColonyConfigMapper.batchInsert(colonyConfigEntityList);
        ampConfigSyncRecordMapper.batchInsert(configSyncRecordList);
    }


}
