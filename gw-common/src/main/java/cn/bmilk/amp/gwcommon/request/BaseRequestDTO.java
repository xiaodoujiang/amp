package cn.bmilk.amp.gwcommon.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequestDTO implements Serializable {

    private String token;
    // 配置中心地址
    private String configCenterAddress;
}
