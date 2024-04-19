package cn.bmilk.amp.nacosgw.utils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class YamlUtils {

    public static String toYaml(Properties properties){
        StringWriter stringWriter = new StringWriter();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        // 创建一个空的 Map 用于保存转换后的数据
        Map<String, Object> yamlData = new HashMap<>();

        // 遍历 Properties 中的键值对，并将其添加到 YAML 数据结构中
        for (String key : properties.stringPropertyNames()) {
            String[] parts = key.split("\\.");
            Map<String, Object> current = yamlData;
            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    // 最后一部分，设置键值对
                   current.put(parts[i], properties.getProperty(key));
                } else {
                    // 非最后一部分，检查并创建子 Map
                    if (!current.containsKey(parts[i])) {
                        current.put(parts[i], new HashMap<String, Object>());
                    }
                    current = (Map<String, Object>) current.get(parts[i]);

                }
            }
        }
        // 将 YAML 数据转换为字符串并返回
        yaml.dump(yamlData, stringWriter);
        return stringWriter.toString();
    }

    public static String toYaml(Map<String, String> configMap) {
        StringWriter stringWriter = new StringWriter();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        // 创建一个空的 Map 用于保存转换后的数据
        Map<String, Object> yamlData = new HashMap<>();

        // 遍历 Properties 中的键值对，并将其添加到 YAML 数据结构中
        for (String key : configMap.keySet()) {
            String[] parts = key.split("\\.");
            Map<String, Object> current = yamlData;
            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    // 最后一部分，设置键值对
                    current.put(parts[i], configMap.get(key));
                } else {
                    // 非最后一部分，检查并创建子 Map
                    if (!current.containsKey(parts[i])) {
                        current.put(parts[i], new HashMap<String, Object>());
                    }
                    if( !(current.get(parts[i]) instanceof Map)){
                        throw new IllegalArgumentException("Conflict: " + parts[i] + " at " + String.join(".", parts) + " is already defined as a value.");
                    }
                    current = (Map<String, Object>) current.get(parts[i]);
                }
            }
        }
        // 将 YAML 数据转换为字符串并返回
        yaml.dump(yamlData, stringWriter);
        return stringWriter.toString();
    }

    public static Map<String, Object> toYamlMap(String yamlStr){
        return new Yaml().load(yamlStr);
    }

    public static Map<String, String> toMap(String yamlStr){
        Yaml yaml = new Yaml();
        Map<String, Object> yamlMap = yaml.load(yamlStr);
        Map<String, String> result = new HashMap<>();
        convertToKeyMap(yamlMap, result, "");
        return result;
    }

    // 将 Map 格式转换为指定键格式的 Map
    private static void convertToKeyMap( Map<String, Object> yamlMap, Map<String, String> result, String prefix) {
        for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                convertToKeyMap((Map<String, Object>) value, result, key);
            } else {
                result.put(key, value.toString());
            }
        }
    }
}
