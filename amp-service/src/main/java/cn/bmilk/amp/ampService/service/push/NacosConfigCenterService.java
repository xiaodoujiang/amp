package cn.bmilk.amp.ampService.service.push;

import cn.bmilk.amp.ampService.common.ConfigTypeEnum;
import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.mapper.*;
import cn.bmilk.amp.ampService.mapper.entity.*;
import cn.bmilk.amp.ampService.remote.NacosGwRemote;
import cn.bmilk.amp.ampService.service.ConfigCenterService;
import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;
import cn.bmilk.amp.gwcommon.response.QueryConfigResponseDTO;
import cn.bmilk.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * nacos做配置中心
 */
@Slf4j
@Service("nacos")
public class NacosConfigCenterService implements ConfigCenterService {

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

    @Override
    public PushConfigResponseDTO push(AmpPushRecordEntity ampPushRecordEntity,
                                      AmpApplicationEntity ampApplicationEntity,
                                      ConfigCenterDetailDTO configCenterDetailDTO) {
        // 查询应用的所有配置项
        List<AmpConfigItemEntity> configItemList = ampConfigItemMapper.queryByAppNameAndEnv(ampApplicationEntity.getAppName(),
                ampPushRecordEntity.getEnvironment());
        // 对于非基础配置依赖根据不同集群进行转换
        assemblyParamConvert(configItemList, ampPushRecordEntity);
        // 推送
        return nacosGwRemote.pushConfig(ampPushRecordEntity, ampApplicationEntity, configItemList, configCenterDetailDTO);
    }

    @Override
    public List<ConfigurationDTO> queryConfigList(AmpApplicationEntity ampApplicationEntity,
                                                  ConfigCenterDetailDTO configCenterDetailDTO,
                                                  String env) {
        QueryConfigResponseDTO queryConfigResponseDTO = nacosGwRemote.queryConfigList(ampApplicationEntity, configCenterDetailDTO, env);
        return queryConfigResponseDTO.getConfigurationDTOList();
    }

    @Override
    public LoginResponseDTO login(String username, String password, String requestAddress, String requestPath) {
        return nacosGwRemote.login(username, password, requestAddress, requestPath);
    }

    @Override
    public ConfigCenterDetailDTO queryConfigCenterDetail(String colonyName, String centerName){
        List<AmpColonyConfigEntity> ampColonyConfigEntityList = ampColonyConfigMapper.queryByAppAndColony(centerName, colonyName);

        ConfigCenterDetailDTO result = new ConfigCenterDetailDTO();

        if(null == ampColonyConfigEntityList || ampColonyConfigEntityList.isEmpty()){
            return result;
        }
        Map<String, AmpColonyConfigEntity> collect = ampColonyConfigEntityList.stream().collect(Collectors.toMap(AmpColonyConfigEntity::getConfigKey, Function.identity()));
        String upperCase = centerName.toUpperCase();
        result.setRequestAddress(collect.get(String.format(CONFIG_CENTER_ADDRESS_FORMAT, upperCase)).getConfigValue());
        result.setRequestPath(collect.get(String.format(CONFIG_CENTER_PATH_FORMAT, upperCase)).getConfigValue());
        result.setUserName(collect.get(String.format(CONFIG_CENTER_USERNAME_FORMAT, upperCase)).getConfigValue());
        result.setPassword(collect.get(String.format(CONFIG_CENTER_PASSWORD_FORMAT, upperCase)).getConfigValue());
        return  result;
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
