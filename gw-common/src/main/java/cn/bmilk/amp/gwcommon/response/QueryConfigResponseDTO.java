package cn.bmilk.amp.gwcommon.response;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryConfigResponseDTO extends BaseResponseDTO{
    // 名称空间
    private String namespace;
    // 分组
    private String group;
    // 应用名称
    private String serverName;
    // 环境
    private String env;
    // 配置项列表
    private List<ConfigurationDTO> configurationDTOList;
}
