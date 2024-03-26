package cn.bmilk.amp.nacosgw.utils;

import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties toProperties(List<ConfigurationDTO> configurationDTOList){
        Properties properties = new Properties();
        if(null == configurationDTOList || configurationDTOList.isEmpty()){
            return properties;
        }
        for (ConfigurationDTO configurationDTO : configurationDTOList){
            properties.setProperty(configurationDTO.getKey(), configurationDTO.getValue());
        }
        return properties;
    }

    public static Properties toProperties(String propertiesStr){
        Properties properties = new Properties();
        if(StringUtils.isBlank(propertiesStr)){
            return properties;
        }
        try (Reader reader = new StringReader(propertiesStr)){
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static Properties toProperties(Map<String, Object> yamlData){
        Properties properties = new Properties();
        processYamlData("", yamlData, properties);
        return properties;
    }

    private static void processYamlData(String prefix, Map<String, Object> data, Properties properties) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                processYamlData(key, (Map<String, Object>) value, properties);
            } else {
                properties.setProperty(key, value.toString());
            }
        }
    }

    public static String toString(Properties properties){
        StringWriter stringWriter = new StringWriter();
        try {
            properties.store(stringWriter, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringWriter.toString();
    }
}
