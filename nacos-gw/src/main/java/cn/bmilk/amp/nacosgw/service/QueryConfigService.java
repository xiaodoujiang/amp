package cn.bmilk.amp.nacosgw.service;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.request.QueryConfigRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.amp.gwcommon.response.QueryConfigResponseDTO;
import cn.bmilk.amp.nacosgw.config.RequestURIEnum;
import cn.bmilk.amp.nacosgw.remote.NacosBaseResponseDTO;
import cn.bmilk.amp.nacosgw.service.config.ConfigureItemService;
import cn.bmilk.tools.http.BaseJerseyFacade;
import cn.bmilk.tools.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("QUERY_CONFIGURATION")
public class QueryConfigService extends AbstractGwService<QueryConfigRequestDTO> {
    @Resource(name = "nacosJerseyFacade")
    private BaseJerseyFacade nacosJersey;

    @Resource
    private Map<String, ConfigureItemService> configureItemServiceMap;

    @Override
    protected String paramValid(QueryConfigRequestDTO requestDTO) {
        StringBuilder sb = new StringBuilder();

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
        if (StringUtils.isBlank(requestDTO.getFileType())) {
            sb.append("FileType  must not null;");
        }
        return sb.toString();
    }

    @Override
    protected QueryConfigRequestDTO parseBusinessRequestDTO(String requestDTOJson) {
        return GsonUtils.fromJson(requestDTOJson, QueryConfigRequestDTO.class);
    }

    @Override
    protected BaseResponseDTO doTransaction(QueryConfigRequestDTO requestDTO) {
        String requestUrl = requestDTO.getConfigCenterAddress() + RequestURIEnum.PUSH_CONFIG.getUri();
        GenericType<NacosBaseResponseDTO<String>> type = new GenericType<NacosBaseResponseDTO<String>>() {
        };
        Map<String, String> map = buildRequestMap(requestDTO);
        NacosBaseResponseDTO<String> nacosBaseResponseDTO = nacosJersey.get(requestUrl, map, type);
        if(nacosBaseResponseDTO.isSuccess()){
            return buildResponse(requestDTO, nacosBaseResponseDTO.getData());
        }
        throw new RuntimeException("query config from nacos failure");
    }

    private Map<String, String> buildRequestMap(QueryConfigRequestDTO requestDTO) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("dataId", requestDTO.getServerName() + "-" + requestDTO.getEnv() + "." + requestDTO.getFileType());
        queryMap.put("group", requestDTO.getGroup());
        queryMap.put("namespaceId", requestDTO.getNamespace());
        queryMap.put("accessToken", requestDTO.getToken());
        return queryMap;
    }

    private QueryConfigResponseDTO buildResponse(QueryConfigRequestDTO requestDTO, String data){
        QueryConfigResponseDTO queryConfigResponseDTO = new QueryConfigResponseDTO();
        queryConfigResponseDTO.setNamespace(requestDTO.getNamespace());
        queryConfigResponseDTO.setEnv(requestDTO.getEnv());
        queryConfigResponseDTO.setGroup(requestDTO.getGroup());
        queryConfigResponseDTO.setServerName(requestDTO.getServerName());
        queryConfigResponseDTO.setConfigurationDTOList(buildConfigList(data, requestDTO.getFileType()));
        return queryConfigResponseDTO;
    }

    private List<ConfigurationDTO> buildConfigList(String configData, String fileType){
        return configureItemServiceMap.get(fileType).toConfiguration(configData);
    }
}
