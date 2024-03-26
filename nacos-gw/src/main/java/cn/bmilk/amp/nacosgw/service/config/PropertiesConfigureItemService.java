package cn.bmilk.amp.nacosgw.service.config;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.nacosgw.utils.PropertiesUtils;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service("properties")
public class PropertiesConfigureItemService implements ConfigureItemService{
    @Override
    public String toString(List<ConfigurationDTO> configurationDTOList) {
        Properties properties = PropertiesUtils.toProperties(configurationDTOList);
        return PropertiesUtils.toString(properties);
    }

    @Override
    public List<ConfigurationDTO> toConfiguration(String configData) {
        Properties properties = PropertiesUtils.toProperties(configData);
        return toConfigList(properties);
    }

    private List<ConfigurationDTO> toConfigList(Properties properties){
        List<ConfigurationDTO> configurationDTOList = new ArrayList<>();
        if(null == properties || properties.isEmpty()){
            return configurationDTOList;
        }
        for (Map.Entry<Object,Object> node : properties.entrySet()){
            ConfigurationDTO configurationDTO = new ConfigurationDTO();
            configurationDTO.setKey((String)node.getKey());
            configurationDTO.setValue((String)node.getValue());
            configurationDTOList.add(configurationDTO);
        }
        return configurationDTOList;
    }
}
