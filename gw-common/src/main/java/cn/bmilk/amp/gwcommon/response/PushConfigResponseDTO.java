package cn.bmilk.amp.gwcommon.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PushConfigResponseDTO extends BaseResponseDTO{
    // 流水号
    private String serialNo;
    // 推送时间
    private Date pushTime;

}
