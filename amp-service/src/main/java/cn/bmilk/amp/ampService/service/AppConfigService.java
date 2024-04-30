package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.common.CommonStatusEnum;
import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.AmpConfigSyncRecordMapper;
import cn.bmilk.amp.ampService.mapper.AmpPushRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigSyncRecordEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;
import cn.bmilk.amp.gwcommon.response.QueryConfigResponseDTO;
import cn.bmilk.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppConfigService {

    @Resource
    private AmpPushRecordMapper ampPushRecordMapper;
    @Resource
    private AmpApplicationMapper ampApplicationMapper;
    @Resource
    private Map<String, ConfigCenterService> configCenterServiceMap;
    @Resource
    private AmpConfigSyncRecordMapper configSyncRecordMapper;

    public ConfigCenterDetailDTO login(String colonyName, String configCenterApp){

        ConfigCenterService configCenterService = configCenterServiceMap.get(configCenterApp);

        // todo  先在redis 里面拿  没有再去远程获取并更新到redis中
        ConfigCenterDetailDTO configCenterDetailDTO = configCenterService.queryConfigCenterDetail(colonyName, configCenterApp);

        LoginResponseDTO login = configCenterService.login(configCenterDetailDTO.getUserName(), configCenterDetailDTO.getPassword(),
                configCenterDetailDTO.getRequestAddress(), configCenterDetailDTO.getRequestPath());
        // todo  token在redis保存
        if(StatusEnum.SUCCESS.name().equals(login.getStatus())){
            configCenterDetailDTO.setToken(login.getToken());
            return configCenterDetailDTO;
        }
        // 没有获取到token  直接失败
        String errMsg = String.format("login failure, configCenterDetailDTO[%s]", GsonUtils.toJson(configCenterDetailDTO));
        log.error(errMsg);
        throw new RuntimeException(errMsg);
    }


    /**
     * 推送配置
     * @param recordId
     * @return
     */
    public void push(long recordId) {
        AmpPushRecordEntity ampPushRecordEntity = ampPushRecordMapper.queryById(recordId);
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(ampPushRecordEntity.getAppName());
        ConfigCenterService configCenterService = configCenterServiceMap.get(ampApplicationEntity.getConfigCenterApp());
        if (configCenterService == null) {
            // todo 抛出异常
            throw new RuntimeException();
        }
        ConfigCenterDetailDTO configCenterDetailDTO = login(ampPushRecordEntity.getColonyName(), ampApplicationEntity.getConfigCenterApp());
        // 乐观锁更新
        int count = ampPushRecordMapper.updateStatus(ampPushRecordEntity.getId(), CommonStatusEnum.NEW.name(),
                CommonStatusEnum.PROCESSING.name(), null, null);
        if(count != 1){
            // todo  日志
            return;
        }
        PushConfigResponseDTO pushConfigResponseDTO = configCenterService.push(ampPushRecordEntity, ampApplicationEntity,configCenterDetailDTO);
        // 更新推送状态
        ampPushRecordMapper.updateStatus(ampPushRecordEntity.getId(), CommonStatusEnum.PROCESSING.name(),
                pushConfigResponseDTO.getStatus(), pushConfigResponseDTO.getErrCode(), pushConfigResponseDTO.getErrMsg());
    }

    /**
     * 同步配置
     * @param recordId
     */
    public void syncConfig(long recordId){
        AmpConfigSyncRecordEntity configSyncRecord = configSyncRecordMapper.queryById(recordId);

        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(configSyncRecord.getAppName());
        ConfigCenterDetailDTO configCenterDetailDTO = login(configSyncRecord.getColonyName(), ampApplicationEntity.getConfigCenterApp());
        ConfigCenterService configCenterService = configCenterServiceMap.get(ampApplicationEntity.getConfigCenterApp());
        if (configCenterService == null) {
            // todo 抛出异常
            throw new RuntimeException();
        }
        List<ConfigurationDTO> configurationDTOList =
                configCenterService.queryConfigList(ampApplicationEntity, configCenterDetailDTO, configSyncRecord.getEnvironment());
        // 配置项落库

    }


    
}
