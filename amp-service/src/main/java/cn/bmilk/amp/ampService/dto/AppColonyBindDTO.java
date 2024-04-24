package cn.bmilk.amp.ampService.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppColonyBindDTO implements Serializable {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 集群名称
     */
    private String colonyName;
    /**
     * 配置项列表
     */
    private List<AppColonyConfigDTO> appColonyConfigDTOList;

}
