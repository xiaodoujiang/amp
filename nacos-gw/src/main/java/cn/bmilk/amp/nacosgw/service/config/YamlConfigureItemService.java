package cn.bmilk.amp.nacosgw.service.config;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.nacosgw.utils.YamlUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("yaml")
public class YamlConfigureItemService implements ConfigureItemService{
    @Override
    public String toString(List<ConfigurationDTO> configurationDTOList) {
        Map<String, String> configMap = toMap(configurationDTOList);

        return YamlUtils.toYaml(configMap);
    }

    @Override
    public List<ConfigurationDTO> toConfiguration(String yamlData) {
        Map<String, String> configMap = YamlUtils.toMap(yamlData);
        return toList(configMap);
    }

    private Map<String, String> toMap(List<ConfigurationDTO> configurationDTOList){
        Map<String, String> configMap = new HashMap<>();
        if( null == configurationDTOList || configurationDTOList.isEmpty()){
            return configMap;
        }
        for (ConfigurationDTO configurationDTO : configurationDTOList){
            configMap.put(configurationDTO.getKey(), configurationDTO.getValue());
        }
        return configMap;
    }
    private List<ConfigurationDTO> toList(Map<String, String> configMap){
        List<ConfigurationDTO> configurationDTOList = new ArrayList<>();

        for (Map.Entry<String, String > node : configMap.entrySet()){
            ConfigurationDTO configurationDTO = new ConfigurationDTO();
            configurationDTO.setKey(node.getKey());
            configurationDTO.setValue(node.getValue());
            configurationDTOList.add(configurationDTO);
        }
        return configurationDTOList;
    }
}
