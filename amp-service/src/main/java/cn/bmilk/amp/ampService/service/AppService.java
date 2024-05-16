package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.AppColonyConfigDTO;
import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.dto.response.ApplicationResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.AmpColonyMapper;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemMapper;
import cn.bmilk.amp.ampService.mapper.entity.*;
import cn.bmilk.amp.ampService.task.ConfigSyncTask;
import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppService {

    @Resource
    private AmpApplicationMapper ampApplicationMapper;
    @Resource
    private AppTransactionalService appTransactionalService;
    @Resource
    private AmpConfigItemMapper ampConfigItemMapper;
    @Resource
    private Map<String, ConfigCenterService> ampPushServiceMap;
    @Resource
    private AmpColonyMapper ampColonyMapper;
    @Resource
    private ThreadPoolTaskExecutor configSyncExecutor;
    @Resource
    private AppConfigService appConfigService;

    public AppDetailResponseDTO queryAppDetail(String appName){
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public AppDetailResponseDTO createApp(ApplicationRequestDTO requestDTO){
        AmpApplicationEntity ampApplicationEntity = AmpApplicationEntity.build(requestDTO);
        ampApplicationMapper.insert(ampApplicationEntity);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public void bindAppColony( List<AppColonyBindDTO> appColonyBindDTOList){
        List<AmpAppColonyRelEntity> appColonyRelEntityList = new ArrayList<>();

        List<AmpColonyConfigEntity> colonyConfigEntityList = new ArrayList<>();
        List<AmpConfigSyncRecordEntity> configSyncRecordList = new ArrayList<>();

        for (AppColonyBindDTO appColonyBindDTO : appColonyBindDTOList){
            AmpAppColonyRelEntity ampAppColonyRelEntity = AmpAppColonyRelEntity.build(appColonyBindDTO);
            appColonyRelEntityList.add(ampAppColonyRelEntity);
            AmpColonyEntity ampColonyEntity = ampColonyMapper.queryByName(appColonyBindDTO.getColonyName());
            AmpConfigSyncRecordEntity configSyncRecord = AmpConfigSyncRecordEntity.build(appColonyBindDTO.getAppName(), appColonyBindDTO.getAppName(), ampColonyEntity.getEnvironment());
            configSyncRecordList.add(configSyncRecord);
            for (AppColonyConfigDTO appColonyConfigDTO : appColonyBindDTO.getAppColonyConfigDTOList()){
                AmpColonyConfigEntity ampColonyConfigEntity = AmpColonyConfigEntity.build(appColonyConfigDTO, appColonyBindDTO);
                colonyConfigEntityList.add(ampColonyConfigEntity);
            }
        }
        appTransactionalService.bindAppColony(appColonyRelEntityList, colonyConfigEntityList, configSyncRecordList);
        for (AmpConfigSyncRecordEntity ampConfigSyncRecordEntity : configSyncRecordList){
            configSyncExecutor.execute(new ConfigSyncTask(ampConfigSyncRecordEntity.getId()));
        }
    }

    public ApplicationResponseDTO queryAppConfigList(String appName, String env, String colonyName){
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        applicationResponseDTO.setAppName(appName);
        List<AmpConfigItemEntity> ampConfigItemEntityList = ampConfigItemMapper.queryByAppNameAndEnv(appName, env);
        applicationResponseDTO.setConfigDetailDTOList(buildConfigList(ampConfigItemEntityList, null));
        if(StringUtils.isNotBlank(colonyName)){
            AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);

            ConfigCenterService configCenterService = ampPushServiceMap.get(ampApplicationEntity.getConfigCenterApp());
            if (configCenterService == null) {
                // todo 抛出异常
                throw new RuntimeException();
            }
            ConfigCenterDetailDTO configCenterDetailDTO = appConfigService.login(colonyName, ampApplicationEntity.getConfigCenterApp());
            List<ConfigurationDTO> configurationDTOList = configCenterService.queryConfigList(ampApplicationEntity, configCenterDetailDTO, env);
            applicationResponseDTO.setConfigDetailDTOList(buildConfigList(ampConfigItemEntityList, configurationDTOList));
        }
        return applicationResponseDTO;
    }



    private List<ConfigDetailDTO> buildConfigList(List<AmpConfigItemEntity> ampConfigItemEntityList, List<ConfigurationDTO> configurationDTOList){
        List<ConfigDetailDTO> result = new ArrayList<>();
        Map<String, ConfigurationDTO> configMap = new HashMap<>();
        if(null != configurationDTOList) {
            configMap = configurationDTOList.stream().collect(Collectors.toMap(ConfigurationDTO::getKey, configurationDTO -> configurationDTO));
        }
        for (AmpConfigItemEntity ampConfigItemEntity : ampConfigItemEntityList){
            ConfigDetailDTO configDetailDTO = new ConfigDetailDTO(ampConfigItemEntity);
            if(configMap.containsKey(configDetailDTO.getConfigKey())){
                configDetailDTO.setConfigValue(configMap.get(configDetailDTO.getConfigKey()).getValue());
            }else {
                throw new RuntimeException("config exist DB but not in config center, configKey[" + configDetailDTO.getConfigKey() +"]");
            }
            result.add(configDetailDTO);
        }
        return result;
    }

}
