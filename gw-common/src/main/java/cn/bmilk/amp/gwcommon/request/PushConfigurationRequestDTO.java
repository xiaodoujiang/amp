package cn.bmilk.amp.gwcommon.request;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PushConfigurationRequestDTO extends BaseRequestDTO{

    // 流水号
    private String serialNo;
    // 命名空间
    private String namespace;
    // 分组
    private String group;
    // 应用名称
    private String serverName;
    // 环境信息
    private String env;
    // 配置文件类型
    private String fileType;
    // 配置项列表
    private List<ConfigurationDTO> configurationDTOList;
}
