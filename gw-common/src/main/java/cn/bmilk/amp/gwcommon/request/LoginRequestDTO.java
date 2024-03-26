package cn.bmilk.amp.gwcommon.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestDTO extends BaseRequestDTO{
    // 登陆用户名
    private String userName;
    // 登陆密码
    private String password;

}
