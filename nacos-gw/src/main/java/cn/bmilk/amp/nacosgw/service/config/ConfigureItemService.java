package cn.bmilk.amp.nacosgw.service.config;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;

import java.util.List;

public interface ConfigureItemService {

    // 配置列表整合为nacos使用的字符串
    String toString(List<ConfigurationDTO> configurationDTOList);

    // 将nacos配置项整合成ConfigurationDTO
    List<ConfigurationDTO> toConfiguration(String configData);
}
