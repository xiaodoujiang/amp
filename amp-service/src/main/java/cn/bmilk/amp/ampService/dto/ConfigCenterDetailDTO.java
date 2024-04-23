package cn.bmilk.amp.ampService.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigCenterDetailDTO implements Serializable {

    private String type;

    private String requestAddress;

    private String requestPath;

    private String userName;

    private String password;

    private String token;

}
