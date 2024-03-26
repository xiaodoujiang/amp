package cn.bmilk.amp.gwcommon.request;

import lombok.Data;

@Data
public class GwRequestDTO {
    private String channel;
    // 业务类型
    private String businessType;
    // 数据json
    private String requestDataJson;

}
