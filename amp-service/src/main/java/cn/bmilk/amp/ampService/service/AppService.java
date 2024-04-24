package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.AppColonyConfigDTO;
import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.dto.response.ApplicationResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpAppColonyRelEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpColonyConfigEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.remote.NacosGwRemote;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppService {

    @Resource
    private AmpApplicationMapper ampApplicationMapper;
    @Resource
    private AppTransactionalService appTransactionalService;
    @Resource
    private AmpConfigItemMapper ampConfigItemMapper;
    @Resource
    private Map<String, AppConfigService> ampPushServiceMap;

    public AppDetailResponseDTO queryAppDetail(String appName){
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public AppDetailResponseDTO createApp(ApplicationRequestDTO requestDTO){
        AmpApplicationEntity ampApplicationEntity = AmpApplicationEntity.build(requestDTO);
        ampApplicationMapper.insert(ampApplicationEntity);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public void bindAppColony(ApplicationRequestDTO requestDTO){
        List<AmpAppColonyRelEntity> appColonyRelEntityList = new ArrayList<>();

        List<AmpColonyConfigEntity> colonyConfigEntityList = new ArrayList<>();

        for (AppColonyBindDTO appColonyBindDTO : requestDTO.getAppColonyBindDTOList()){
            AmpAppColonyRelEntity ampAppColonyRelEntity = AmpAppColonyRelEntity.build(appColonyBindDTO);
            appColonyRelEntityList.add(ampAppColonyRelEntity);
            for (AppColonyConfigDTO appColonyConfigDTO : appColonyBindDTO.getAppColonyConfigDTOList()){
                AmpColonyConfigEntity ampColonyConfigEntity = AmpColonyConfigEntity.build(appColonyConfigDTO, appColonyBindDTO);
                colonyConfigEntityList.add(ampColonyConfigEntity);
            }
        }
        appTransactionalService.bindAppColony(appColonyRelEntityList, colonyConfigEntityList);
    }

    public ApplicationResponseDTO queryAppConfigList(String appName, String env, String colonyName){
        ApplicationResponseDTO applicationResponseDTO = new ApplicationResponseDTO();
        applicationResponseDTO.setAppName(appName);
        List<AmpConfigItemEntity> ampConfigItemEntityList = ampConfigItemMapper.queryByAppNameAndEnv(appName, env);
        applicationResponseDTO.setConfigDetailDTOList(buildConfigList(ampConfigItemEntityList, null));
        if(StringUtils.isNotBlank(colonyName)){
            AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);

            AppConfigService appConfigService = ampPushServiceMap.get(ampApplicationEntity.getConfigCenterApp());
            if (appConfigService == null) {
                // todo 抛出异常
                throw new RuntimeException();
            }
            Map<String, String> configMap = appConfigService.queryAppConfig(ampApplicationEntity, colonyName, env);
            applicationResponseDTO.setConfigDetailDTOList(buildConfigList(ampConfigItemEntityList, null));
        }
        return applicationResponseDTO;
    }



    private List<ConfigDetailDTO> buildConfigList(List<AmpConfigItemEntity> ampConfigItemEntityList, Map<String, String> configMap){
        List<ConfigDetailDTO> result = new ArrayList<>();
        for (AmpConfigItemEntity ampConfigItemEntity : ampConfigItemEntityList){
            ConfigDetailDTO configDetailDTO = new ConfigDetailDTO(ampConfigItemEntity);
            if(null != configMap){
                if(configMap.containsKey(configDetailDTO.getConfigKey())){
                    configDetailDTO.setConfigValue(configMap.get(configDetailDTO.getConfigKey()));
                }else {
                    throw new RuntimeException("config exist DB but not in config center, configKey[" + configDetailDTO.getConfigKey() +"]");
                }
            }
            result.add(configDetailDTO);
        }
        return result;
    }

}
