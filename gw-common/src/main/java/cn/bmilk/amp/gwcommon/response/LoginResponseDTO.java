package cn.bmilk.amp.gwcommon.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponseDTO extends BaseResponseDTO{

    // 登陆用户名
    private String userName;
    // 登录结果token
    private String token;
}
