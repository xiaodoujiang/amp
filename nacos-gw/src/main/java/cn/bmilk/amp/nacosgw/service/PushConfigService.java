package cn.bmilk.amp.nacosgw.service;

import cn.bmilk.amp.gwcommon.common.StatusEnum;
import cn.bmilk.amp.gwcommon.request.PushConfigurationRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.amp.gwcommon.response.PushConfigResponseDTO;
import cn.bmilk.amp.nacosgw.config.RequestURIEnum;
import cn.bmilk.amp.nacosgw.remote.NacosBaseResponseDTO;
import cn.bmilk.amp.nacosgw.service.config.ConfigureItemService;
import cn.bmilk.tools.http.BaseJerseyFacade;
import cn.bmilk.tools.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.core.GenericType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("PUSH_CONFIGURATION")
public class PushConfigService extends AbstractGwService<PushConfigurationRequestDTO> {

    @Resource(name = "nacosJerseyFacade")
    private BaseJerseyFacade nacosJersey;

    @Resource
    private Map<String, ConfigureItemService> configureItemServiceMap;

    @Override
    protected PushConfigurationRequestDTO parseBusinessRequestDTO(String requestDTOJson) {
        PushConfigurationRequestDTO pushConfigurationRequestDTO = GsonUtils.fromJson(requestDTOJson, PushConfigurationRequestDTO.class);
        if (null == pushConfigurationRequestDTO) {
            pushConfigurationRequestDTO = new PushConfigurationRequestDTO();
        }
        return pushConfigurationRequestDTO;
    }

    @Override
    protected BaseResponseDTO doTransaction(PushConfigurationRequestDTO requestDTO) {
        String requestUrl = requestDTO.getConfigCenterAddress() + RequestURIEnum.PUSH_CONFIG.getUri();
        GenericType<NacosBaseResponseDTO<Boolean>> type = new GenericType<NacosBaseResponseDTO<Boolean>>() {
        };
        NacosBaseResponseDTO<Boolean> pushResponse = nacosJersey.post(requestUrl, buildRemoteRequestDTO(requestDTO), type);
        return buildResponseDTO(pushResponse, requestDTO);
    }


    private Map<String, String> buildRemoteRequestDTO(PushConfigurationRequestDTO requestDTO) {
        Map<String, String> pushConfigMap = new HashMap<>();
        pushConfigMap.put("dataId", requestDTO.getServerName() + "-" + requestDTO.getEnv() + "." + requestDTO.getFileType());
        pushConfigMap.put("group", requestDTO.getGroup());
        pushConfigMap.put("namespaceId", requestDTO.getNamespace());
        pushConfigMap.put("accessToken", requestDTO.getToken());
        pushConfigMap.put("content", buildContent(requestDTO));
        pushConfigMap.put("type", requestDTO.getFileType());
        return pushConfigMap;
    }

    private String buildContent(PushConfigurationRequestDTO requestDTO) {
        return configureItemServiceMap.get(requestDTO.getFileType()).toString(requestDTO.getConfigurationDTOList());
    }

    private PushConfigResponseDTO buildResponseDTO(NacosBaseResponseDTO<Boolean> pushResponse,
                                                   PushConfigurationRequestDTO requestDTO) {
        PushConfigResponseDTO pushConfigResponseDTO = new PushConfigResponseDTO();
        StatusEnum pushStatus = pushResponse.isSuccess() && pushResponse.getData() ? StatusEnum.SUCCESS : StatusEnum.FAILURE;
        pushConfigResponseDTO.setStatus(pushStatus.name());
        pushConfigResponseDTO.setSerialNo(requestDTO.getSerialNo());
        pushConfigResponseDTO.setPushTime(new Date());
        return pushConfigResponseDTO;
    }


    @Override
    protected String paramValid(PushConfigurationRequestDTO requestDTO) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isBlank(requestDTO.getSerialNo())) {
            sb.append("SerialNo  must not null; ");
        }
        if (StringUtils.isBlank(requestDTO.getNamespace())) {
            sb.append("Namespace  must not null;");
        }
        if (StringUtils.isBlank(requestDTO.getConfigCenterAddress())) {
            sb.append("configCenterAddress  must not null;");
        }
        if (StringUtils.isBlank(requestDTO.getGroup())) {
            sb.append("Group  must not null;");
        }
        if (StringUtils.isBlank(requestDTO.getServerName())) {
            sb.append("ServerName  must not null;");
        }
        if (StringUtils.isBlank(requestDTO.getEnv())) {
            sb.append("env  must not null;");
        }
        return sb.toString();
    }
}
