package cn.bmilk.amp.ampService.remote;

import cn.bmilk.amp.ampService.config.AppProperties;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.common.BusinessTypeEnum;
import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.request.LoginRequestDTO;
import cn.bmilk.amp.gwcommon.request.PushConfigurationRequestDTO;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;
import cn.bmilk.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class NacosGwRemote {

    @Resource
    private NacosGwClient nacosGwClient;


    public LoginResponseDTO login(String username, String password, String appAddress, String path){
        LoginResponseDTO loginResponseDTO = null;
        GwRequestDTO gwRequestDTO = null;
        try {
            gwRequestDTO = buildLoginRequestDTO(username, password, appAddress + path);
            String responseStr =  nacosGwClient.distribute(gwRequestDTO);
            loginResponseDTO = GsonUtils.fromJson(responseStr, LoginResponseDTO.class);
        }catch (Exception e){
            log.error("nacos-gw login failure, gwRequestDTO[{}], errMsg[{}]", gwRequestDTO, e.getMessage(), e);
            loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setStatus(StatusEnum.FAILURE.name());
        }
        return loginResponseDTO;
    }


    public PushConfigResponseDTO pushConfig(String token,
                                            String appAddress,
                                            String path,
                                            AmpPushRecordEntity ampPushRecordEntity,
                                            AmpApplicationEntity ampApplicationEntity,
                                            List<AmpConfigItemEntity> configItemEntityList){
        PushConfigResponseDTO pushConfigResponseDTO = null;
        GwRequestDTO gwRequestDTO = null;
        try {
            gwRequestDTO = buildPushConfigurationRequestDTO(token, appAddress + path,
                    ampPushRecordEntity, ampApplicationEntity, configItemEntityList);
            String responseStr = nacosGwClient.distribute(gwRequestDTO);
            pushConfigResponseDTO = GsonUtils.fromJson(responseStr, PushConfigResponseDTO.class);
        }catch (Exception e){
            log.error("nacos-gw pushConfig failure, gwRequestDTO[{}], errMsg[{}]", gwRequestDTO, e.getMessage(), e);
            pushConfigResponseDTO = new PushConfigResponseDTO();
            pushConfigResponseDTO.setStatus(StatusEnum.FAILURE.name());
        }
        return pushConfigResponseDTO;
    }





    private GwRequestDTO buildLoginRequestDTO(String username, String password, String configCenterAddress){
        LoginRequestDTO loginRequestDTO = LoginRequestDTO.build(username, password, configCenterAddress);

        return new GwRequestDTO(AppProperties.APP_CHANEL, BusinessTypeEnum.AUTH_LOGIN.name(), GsonUtils.toJson(loginRequestDTO));
    }

    private GwRequestDTO buildPushConfigurationRequestDTO(String token,
                                                          String configCenterAddress,
                                                          AmpPushRecordEntity ampPushRecordEntity,
                                                          AmpApplicationEntity ampApplicationEntity,
                                                          List<AmpConfigItemEntity> configItemEntityList){

        List< ConfigurationDTO> configurationDTOList = new ArrayList<>();
        for (AmpConfigItemEntity ampConfigItemEntity : configItemEntityList){
            ConfigurationDTO configurationDTO = new ConfigurationDTO();
            configurationDTO.setKey(ampConfigItemEntity.getConfigKey());
            configurationDTO.setValue(ampConfigItemEntity.getConfigValue());
            configurationDTO.setDesc(ampConfigItemEntity.getConfigDesc());
            configurationDTOList.add(configurationDTO);
        }

        PushConfigurationRequestDTO pushConfigurationRequestDTO = new PushConfigurationRequestDTO();
        pushConfigurationRequestDTO.setToken(token);
        pushConfigurationRequestDTO.setConfigCenterAddress(configCenterAddress);
        pushConfigurationRequestDTO.setNamespace(ampApplicationEntity.getConfigTenant());
        pushConfigurationRequestDTO.setFileType(ampApplicationEntity.getConfigFileType());
        pushConfigurationRequestDTO.setEnv(ampPushRecordEntity.getEnvironment());
        pushConfigurationRequestDTO.setGroup(ampApplicationEntity.getConfigGroup());
        pushConfigurationRequestDTO.setServerName(ampApplicationEntity.getApplicationName());
        pushConfigurationRequestDTO.setSerialNo(ampPushRecordEntity.getSerialNo());
        pushConfigurationRequestDTO.setConfigurationDTOList(configurationDTOList);

        return new GwRequestDTO(AppProperties.APP_CHANEL, BusinessTypeEnum.PUSH_CONFIGURATION.name(), GsonUtils.toJson(pushConfigurationRequestDTO));
    }
}
