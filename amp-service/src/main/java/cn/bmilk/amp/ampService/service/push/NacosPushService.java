package cn.bmilk.amp.ampService.service.push;

import cn.bmilk.amp.ampService.common.AmpPushStatusEnum;
import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.mapper.AmpAppColonyRelMapper;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemMapper;
import cn.bmilk.amp.ampService.mapper.AmpPushRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpAppColonyRelEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.ampService.remote.NacosGwRemote;
import cn.bmilk.amp.ampService.service.AmpPushService;
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
        // 查询集群的配置中心地址用户名
        AmpAppColonyRelEntity ampAppColonyRelEntity = ampAppColonyRelMapper.queryByAppAndColony(ampApplicationEntity.getConfigCenterApp(), ampPushRecordEntity.getColonyName());
        // 获取登录token
        String token = null;
        try {
            token = login(ampAppColonyRelEntity);
        }catch (Exception e){
            // 更新推送状态为失败
            return false;
        }
        // 推送
        PushConfigResponseDTO pushConfigResponseDTO = nacosGwRemote.pushConfig(token, ampAppColonyRelEntity.getAppRequestAddressValue(),
                ampAppColonyRelEntity.getAppRequestAddressValue(), ampPushRecordEntity, ampApplicationEntity, configItemList);
        // 更新推送状态
        ampPushRecordMapper.updateStatus(ampPushRecordEntity.getId(), AmpPushStatusEnum.PROCESSING.name(),
                pushConfigResponseDTO.getStatus(), pushConfigResponseDTO.getErrCode(), pushConfigResponseDTO.getErrMsg());
        // 返回推送结果
        return StatusEnum.SUCCESS.name().equals(pushConfigResponseDTO.getStatus());
    }


    public String login(AmpAppColonyRelEntity ampAppColonyRelEntity){
        // todo  先在redis 里面拿  没有再去远程获取并更新到redis中

        LoginResponseDTO login = nacosGwRemote.login(ampAppColonyRelEntity.getAppUsername(), ampAppColonyRelEntity.getAppPassword(),
                ampAppColonyRelEntity.getAppRequestAddressValue(), ampAppColonyRelEntity.getAppRequestPathValue());
        // todo  token在redis保存
        if(StatusEnum.SUCCESS.name().equals(login.getStatus())){
            return login.getToken();
        }
        // 没有获取到token  直接失败
        throw new RuntimeException();
    }

    private void assemblyParamConvert(List<AmpConfigItemEntity> configItemList,
                                      AmpPushRecordEntity ampPushRecordEntity){
        Map<String, AmpConfigItemEntity> tmp = new HashMap<>();
        List<String> appNameList = new ArrayList<>();
        if(null == configItemList || configItemList.isEmpty()) return;
        for (AmpConfigItemEntity item : configItemList){
            if(ConfigTypeEnum.NORMAL_CONFIG.name().equals(item.getConfigType())){
                break;
            }
            appNameList.add(item.getDependAppName());
            tmp.put(item.getDependAppName()+ item.getConfigKey(), item);
        }
        if(appNameList.isEmpty()) return;
        List<AmpAppColonyRelEntity>  ampAppColonyRelEntityList = ampAppColonyRelMapper.queryByAppListAndColony(appNameList,
                ampPushRecordEntity.getColonyName());
        for (AmpAppColonyRelEntity entity : ampAppColonyRelEntityList){
            String key = entity.getAppName() + entity.getAppRequestAddressKey();
            if(tmp.containsKey(key)){
                AmpConfigItemEntity ampConfigItemEntity = tmp.remove(key);
                ampConfigItemEntity.setConfigValue(entity.getAppRequestAddressValue());
            }
            key = entity.getAppName() + entity.getAppRequestPathKey();
            if(tmp.containsKey(key)){
                AmpConfigItemEntity ampConfigItemEntity = tmp.remove(key);
                ampConfigItemEntity.setConfigValue(entity.getAppRequestPathValue());
            }
        }
        if(tmp.isEmpty())  return;
        log.error("find some config not have value, configList[{}]", configItemList);
        throw new RuntimeException("find some config not have value");
    }

}
