package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;

import java.util.List;
import java.util.Map;


public interface ConfigCenterService {

    String CONFIG_CENTER_ADDRESS_FORMAT = "[%s_REQUEST_ADDRESS]";
    String CONFIG_CENTER_PATH_FORMAT = "[%s_REQUEST_PATH]";
    String CONFIG_CENTER_USERNAME_FORMAT = "[%s_REQUEST_USERNAME]";
    String CONFIG_CENTER_PASSWORD_FORMAT = "[%s_REQUEST_PASSWORD]";


    /**
     * 登录
     * @return
     */
    LoginResponseDTO login(String username, String password, String requestAddress, String requestPath);

    /**
     * 查询配置中心详细信息
     * @param colonyName
     * @param centerName
     * @return
     */
    ConfigCenterDetailDTO queryConfigCenterDetail(String colonyName, String centerName);

    /**
     * 推送
     * @param ampPushRecordEntity
     * @param ampApplicationEntity
     * @return
     */
    PushConfigResponseDTO push(AmpPushRecordEntity ampPushRecordEntity,
                               AmpApplicationEntity ampApplicationEntity,
                               ConfigCenterDetailDTO configCenterDetailDTO);

    /**
     * 查询配置中心中所有配置项
     *
     * @param applicationEntity
     * @param env
     * @return
     */
    List<ConfigurationDTO> queryConfigList(AmpApplicationEntity applicationEntity,
                                           ConfigCenterDetailDTO configCenterDetailDTO,
                                           String env);




}
