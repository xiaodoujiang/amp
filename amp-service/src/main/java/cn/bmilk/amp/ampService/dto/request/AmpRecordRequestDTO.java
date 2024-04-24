package cn.bmilk.amp.ampService.dto.request;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AmpRecordRequestDTO {
    /**
     * ampNo
     */
    private String ampNo;

    /**
     * 环境列表
     */
    private List<String> environmentList;
    /**
     * 应用id
     */
    private Long appId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 项目描述
     */
    private String ampDesc;
    /**
     * 关联Task地址
     */
    private String ampTaskRel;
    /**
     * 预计上线日期
     */
    private Date launchDate;
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 类型-CREATE？UPDATE
     */
    private String type;

    /**
     * 配置项列表d
     */
    private Map<String, List<ConfigDetailDTO>> envConfigMap;

}
