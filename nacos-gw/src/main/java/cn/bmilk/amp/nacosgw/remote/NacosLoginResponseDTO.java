package cn.bmilk.amp.nacosgw.remote;

import lombok.Data;

@Data
public class NacosLoginResponseDTO {

    // 用户名
    private String username;
    // token有效期
    private long tokenTtl;
    // 是否是管理员账户
    private boolean globalAdmin;
    // token
    private String accessToken;

}
