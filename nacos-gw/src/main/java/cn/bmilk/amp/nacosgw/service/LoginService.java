package cn.bmilk.amp.nacosgw.service;

import cn.bmilk.amp.gwcommon.request.LoginRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.amp.gwcommon.response.LoginResponseDTO;
import cn.bmilk.amp.nacosgw.config.RequestURIEnum;
import cn.bmilk.amp.nacosgw.remote.NacosLoginResponseDTO;
import cn.bmilk.tools.http.BaseJerseyFacade;
import cn.bmilk.tools.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("AUTH_LOGIN")
public class LoginService extends AbstractGwService<LoginRequestDTO> {

    @Resource(name = "nacosJerseyFacade")
    private BaseJerseyFacade nacosJersey;


    @Override
    protected String paramValid(LoginRequestDTO requestDTO) {
        StringBuilder sb = new StringBuilder();

        if(StringUtils.isBlank(requestDTO.getUserName())){
            sb.append("userName  must not null;");
        }
        if(StringUtils.isBlank(requestDTO.getPassword())){
            sb.append("password  must not null;");
        }
        if(StringUtils.isBlank(requestDTO.getConfigCenterAddress())){
            sb.append("configCenterAddress  must not null;");
        }
        return sb.toString();
    }

    @Override
    protected LoginRequestDTO parseBusinessRequestDTO(String requestDTOJson) {
        LoginRequestDTO loginRequestDTO = GsonUtils.fromJson(requestDTOJson, LoginRequestDTO.class);
        if(null == loginRequestDTO){
            loginRequestDTO = new LoginRequestDTO();
        }
        return loginRequestDTO;
    }

    @Override
    protected BaseResponseDTO doTransaction(LoginRequestDTO requestDTO) {
        String requestUrl = requestDTO.getConfigCenterAddress() + RequestURIEnum.LOGIN.getUri();
        NacosLoginResponseDTO loginResponse = nacosJersey.post(requestUrl, buildRemoteRequestDTO(requestDTO), NacosLoginResponseDTO.class);
        return buildResponse(loginResponse);
    }

    private Map<String, String> buildRemoteRequestDTO(LoginRequestDTO requestDTO){
        Map<String, String> loginRequestMap = new HashMap<>();
        loginRequestMap.put("username",requestDTO.getUserName());
        loginRequestMap.put("password", requestDTO.getPassword());
        return loginRequestMap;
    }

    private LoginResponseDTO buildResponse(NacosLoginResponseDTO loginResponse){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setUserName(loginResponse.getUsername());
        loginResponseDTO.setToken(loginResponse.getAccessToken());
        return loginResponseDTO;
    }
}
