package cn.bmilk.amp.nacosgw.utils;


import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.common.BusinessTypeEnum;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.request.PushConfigurationDTO;
import cn.bmilk.tools.utils.GsonUtils;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.junit.Test;

import java.util.*;

public class YamlUtilsTest {

    public void Strui(){

        GwRequestDTO gwRequestDTO = new GwRequestDTO();

        gwRequestDTO.setChannel("test");
        gwRequestDTO.setBusinessType(BusinessTypeEnum.PUSH_CONFIGURATION.name());

        PushConfigurationDTO pushConfigurationDTO = new PushConfigurationDTO();
        pushConfigurationDTO.setEnv("dev");
        pushConfigurationDTO.setNamespace("public");
        pushConfigurationDTO.setSerialNo("1111111");
        pushConfigurationDTO.setFileType("yaml");
        pushConfigurationDTO.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYWNvcyIsImV4cCI6MTcxMDkzMTEzMn0.ZbTffsKK7BzKfwgOhRU1Hz8Rsonu1kIQALUczr8FBt0");
        pushConfigurationDTO.setServerName("test");
        pushConfigurationDTO.setGroup("DEFAULT_GROUP");
        pushConfigurationDTO.setConfigCenterAddress("http://192.168.56.11:8848");


        List<ConfigurationDTO> configurationDTOList = new ArrayList<>();
        for (int i = 0; i<5; i++){
            ConfigurationDTO configurationDTO = new ConfigurationDTO();
            String replace = UUID.randomUUID().toString().replace("-", ".");
            configurationDTO.setKey(replace);
            configurationDTO.setValue(UUID.randomUUID().toString());
            configurationDTO.setDesc(Integer.toString(i));
            configurationDTOList.add(configurationDTO);
        }
        pushConfigurationDTO.setConfigurationDTOList(configurationDTOList);


        String json = GsonUtils.toJson(pushConfigurationDTO);
        gwRequestDTO.setRequestDataJson(json);
        System.out.println(GsonUtils.toJson(gwRequestDTO));
    }

    @Test
    public void testToYaml(){

        Map<String , String> map = new HashMap<>();
        map.put("584bbad2.c9ec.4338.a267.ed65cd613328", "584bbad2");
        map.put("a1c76f77.340d.4398.8231.0c73935e6596", "a1c76f77");
        map.put("7bca2a88.dc33.4ed9.9b75.baaebddc9538", "7bca2a88");
        map.put("cc655d8d.0c13.499d.9db7.e6d21140621d", "cc655d8d");
        map.put("d5da065e.2715.466a.9505.2194486e2542", "d5da065e");

        String yaml = YamlUtils.toYaml(map);
        System.out.println(yaml);
    }
}
