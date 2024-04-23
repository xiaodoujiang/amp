package cn.bmilk.amp.ampService.service.push;

import cn.bmilk.amp.ampService.common.AmpPushStatusEnum;
import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.mapper.*;
import cn.bmilk.amp.ampService.mapper.entity.*;
import cn.bmilk.amp.ampService.remote.NacosGwRemote;
import cn.bmilk.amp.ampService.service.AmpPushService;
import cn.bmilk.amp.ampService.service.ConfigCenterService;
import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * nacos做配置中心
 */
@Slf4j
@Service("nacos")
public class NacosPushService implements AmpPushService {

    @Resource
    private NacosGwRemote nacosGwRemote;
    @Resource
    private AmpApplicationMapper ampApplicationMapper;
    @Resource
    private AmpAppColonyRelMapper ampAppColonyRelMapper;
    @Resource
    private AmpConfigItemMapper ampConfigItemMapper;
    @Resource
    private AmpPushRecordMapper ampPushRecordMapper;
    @Resource
    private AmpColonyConfigMapper ampColonyConfigMapper;
    @Resource
    private ConfigCenterService configCenterService;


    public boolean push(AmpPushRecordEntity ampPushRecordEntity,
                        AmpApplicationEntity ampApplicationEntity){
        // 乐观锁更新
        int count = ampPushRecordMapper.updateStatus(ampPushRecordEntity.getId(), AmpPushStatusEnum.NEW.name(),
                AmpPushStatusEnum.PROCESSING.name(), null, null);
        if(count != 1){
            // todo  日志
            return false;
        }
        // 查询应用的所有配置项
        List<AmpConfigItemEntity> configItemList = ampConfigItemMapper.queryByAppNameAndEnv(ampApplicationEntity.getApplicationName(),
                ampPushRecordEntity.getEnvironment());
        // 对于非基础配置依赖根据不同集群进行转换
        assemblyParamConvert(configItemList, ampPushRecordEntity);
        // 推送
        PushConfigResponseDTO pushConfigResponseDTO = nacosGwRemote.pushConfig(ampPushRecordEntity, ampApplicationEntity, configItemList);
        // 更新推送状态
        ampPushRecordMapper.updateStatus(ampPushRecordEntity.getId(), AmpPushStatusEnum.PROCESSING.name(),
                pushConfigResponseDTO.getStatus(), pushConfigResponseDTO.getErrCode(), pushConfigResponseDTO.getErrMsg());
        // 返回推送结果
        return StatusEnum.SUCCESS.name().equals(pushConfigResponseDTO.getStatus());
    }


    private void assemblyParamConvert(List<AmpConfigItemEntity> configItemList,
                                      AmpPushRecordEntity ampPushRecordEntity){
        Map<String, AmpConfigItemEntity> dependency = new HashMap<>();
        if(null == configItemList || configItemList.isEmpty()) return;
        for (AmpConfigItemEntity item : configItemList){
            if(ConfigTypeEnum.APP_DEPENDENCY.name().equals(item.getConfigType()) ||
                    ConfigTypeEnum.BASIC_DEPENDENCY.name().equals(item.getConfigType())){
                dependency.put(item.getConfigValue(), item);
            }
        }
        if(dependency.isEmpty()) return;
        List<AmpColonyConfigEntity>  ampColonyConfigEntityList = ampColonyConfigMapper.queryByKeyListAndColony(dependency.keySet(),
                ampPushRecordEntity.getColonyName());
        for (AmpColonyConfigEntity entity : ampColonyConfigEntityList){
            if(dependency.containsKey(entity.getConfigKey())){
                AmpConfigItemEntity ampConfigItemEntity = dependency.remove(entity.getConfigKey());
                ampConfigItemEntity.setConfigValue(entity.getConfigValue());
            }
        }
        if(dependency.isEmpty())  return;
        log.error("find some config not have value, configList[{}]", configItemList);
        throw new RuntimeException("find some config not have value");
    }
}
