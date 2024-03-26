package cn.bmilk.amp.nacosgw.remote;

import lombok.Data;

@Data
public class NacosLoginRequestDTO {

    // 用户名
    private String userName;
    // 密码
    private String password;
}
