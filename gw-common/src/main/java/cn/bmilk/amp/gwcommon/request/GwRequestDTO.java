package cn.bmilk.amp.gwcommon.request;

import lombok.Data;

@Data
public class GwRequestDTO {
    private String channel;
    // 业务类型
    private String businessType;
    // 数据json
    private String requestDataJson;

    public GwRequestDTO() {
    }
    public GwRequestDTO(String channel, String businessType, String requestDataJson){
        this.channel = channel;
        this.businessType = businessType;
        this.requestDataJson = requestDataJson;
    }

}
