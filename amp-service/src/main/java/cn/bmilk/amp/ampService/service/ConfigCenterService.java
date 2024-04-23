package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.ConfigCenterDetailDTO;
import cn.bmilk.amp.ampService.mapper.AmpColonyConfigMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpAppColonyRelEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpColonyConfigEntity;
import cn.bmilk.amp.ampService.remote.NacosGwRemote;
import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.tools.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConfigCenterService {

    private static final String CONFIG_CENTER_ADDRESS_FORMAT = "[%s_REQUEST_ADDRESS]";
    private static final String CONFIG_CENTER_PATH_FORMAT = "[%s_REQUEST_PATH]";
    private static final String CONFIG_CENTER_USERNAME_FORMAT = "[%s_REQUEST_USERNAME]";
    private static final String CONFIG_CENTER_PASSWORD_FORMAT = "[%s_REQUEST_PASSWORD]";

    @Resource
    private AmpColonyConfigMapper ampColonyConfigMapper;
    @Resource
    private NacosGwRemote nacosGwRemote;



    public ConfigCenterDetailDTO login(String colonyName, String configCenterApp){
        // todo  先在redis 里面拿  没有再去远程获取并更新到redis中
        ConfigCenterDetailDTO configCenterDetailDTO = queryConfigCenterDetail(colonyName, configCenterApp);

        LoginResponseDTO login = nacosGwRemote.login(configCenterDetailDTO.getUserName(), configCenterDetailDTO.getPassword(),
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


}
