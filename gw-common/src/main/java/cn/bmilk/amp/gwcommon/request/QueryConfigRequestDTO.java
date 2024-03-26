package cn.bmilk.amp.gwcommon.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryConfigRequestDTO extends BaseRequestDTO{
    // 名称空间
    private String namespace;
    // 分组
    private String group;
    // 应用名称
    private String serverName;
    // 环境
    private String env;
    // 配置文件类型
    private String fileType;


}
