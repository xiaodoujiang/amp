package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.common.AmpPushStatusEnum;
import cn.bmilk.amp.ampService.common.AmpStatusEnum;
import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.common.ConfigUpdateTypeEnum;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.exception.OptimisticLockingException;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemMapper;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemTmpMapper;
import cn.bmilk.amp.ampService.mapper.AmpPushRecordMapper;
import cn.bmilk.amp.ampService.mapper.AmpRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AmpTransactionalService {

    @Resource
    private AmpRecordMapper ampRecordMapper;

    @Resource
    private AmpConfigItemTmpMapper ampConfigItemTmpMapper;
    @Resource
    private AmpConfigItemMapper ampConfigItemMapper;
    @Resource
    private AmpPushRecordMapper ampPushRecordMapper;

    @Transactional
    public void createAmp(List<AmpRecordEntity> ampRecordEntityList, List<AmpConfigItemTmpEntity> configList) {
        ampRecordMapper.batchInsert(ampRecordEntityList);
        ampConfigItemTmpMapper.batchInsert(configList);
    }

    @Transactional
    public void deleteAmp(String ampNo) {
        ampConfigItemTmpMapper.deleteConfigByAmpNo(ampNo);
        ampRecordMapper.deleteAmpRecord(ampNo);
    }

    @Transactional
    public List<AmpPushRecordEntity> recordPush(AmpRecordEntity ampRecordEntity,
                                                List<String> colonyList,
                                                List<AmpConfigItemTmpEntity> ampConfigItemTmpEntityList) {
        // 更新推送状态为推送中
        int count = ampRecordMapper.updateStatus(ampRecordEntity.getAmpNo(), AmpStatusEnum.NEW.name(), AmpStatusEnum.PUSH_PROCESSING.name());
        if(1 != count){
            return ampPushRecordMapper.queryByAmpNo(ampRecordEntity.getAmpNo());
        }

        // 更新配置项
        List<AmpConfigItemEntity> ampConfigItemList = buildAmpConfigItemList(ampRecordEntity, ampConfigItemTmpEntityList);
        ampConfigItemMapper.batchInset(ampConfigItemList);

        // 插入推送集群
        List<AmpPushRecordEntity> ampPushRecordEntityList = buildAmpRecordList(ampRecordEntity, colonyList);
        ampPushRecordMapper.batchInsert(ampPushRecordEntityList);
        return ampPushRecordEntityList;
    }


    private List<AmpConfigItemEntity> buildAmpConfigItemList(AmpRecordEntity ampRecordEntity,
                                                             List<AmpConfigItemTmpEntity> ampConfigItemTmpEntityList) {
        List<AmpConfigItemEntity> result = new ArrayList<>();
        for (AmpConfigItemTmpEntity ampConfigItemTmpEntity : ampConfigItemTmpEntityList) {
            AmpConfigItemEntity ampConfigItemEntity = new AmpConfigItemEntity();
            ampConfigItemEntity.setApplicationId(ampRecordEntity.getApplicationId());
            ampConfigItemEntity.setApplicationName(ampRecordEntity.getApplicationName());
            ampConfigItemEntity.setConfigKey(ampConfigItemTmpEntity.getConfigKey());
            ampConfigItemEntity.setConfigValue(ampConfigItemTmpEntity.getConfigValue());
            ampConfigItemEntity.setConfigDesc(ampConfigItemTmpEntity.getConfigDesc());
            ampConfigItemEntity.setConfigType(ampConfigItemTmpEntity.getConfigType());
            result.add(ampConfigItemEntity);
        }
        return result;
    }

    private List<AmpPushRecordEntity> buildAmpRecordList(AmpRecordEntity ampRecordEntity,
                                                         List<String> colonyList) {
        List<AmpPushRecordEntity> result = new ArrayList<>();
        for (String colony : colonyList) {
            AmpPushRecordEntity ampPushRecordEntity = new AmpPushRecordEntity();
            ampPushRecordEntity.setAmpNo(ampRecordEntity.getAmpNo());
            ampPushRecordEntity.setEnvironment(ampPushRecordEntity.getEnvironment());
            ampPushRecordEntity.setApplicationName(ampRecordEntity.getApplicationName());
            ampPushRecordEntity.setColonyName(colony);
            ampPushRecordEntity.setPushStatus(AmpPushStatusEnum.NEW.name());
            result.add(ampPushRecordEntity);
        }
        return result;

    }


}
