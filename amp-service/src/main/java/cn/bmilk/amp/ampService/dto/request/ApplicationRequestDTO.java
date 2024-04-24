package cn.bmilk.amp.ampService.dto.request;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApplicationRequestDTO implements Serializable {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用描述
     */
    private String appDesc;
    /**
     * 应用类型
     */
    private String appType;
    /**
     * 配置文件类型
     */
    private String configFileType;
    /**
     * 配置中心app
     */
    private String configCenterApp;
    /**
     * 配置文件分组
     */
    private String configGroup;
    /**
     * 配置文件租户
     */
    private String configTenant;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 绑定集群信息
     */
    private List<AppColonyBindDTO> appColonyBindDTOList;
}
