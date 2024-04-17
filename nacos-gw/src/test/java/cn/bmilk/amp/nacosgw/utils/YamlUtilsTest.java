package cn.bmilk.amp.nacosgw.utils;


import cn.bmilk.amp.gwcommon.ConfigurationDTO;
import cn.bmilk.amp.gwcommon.common.BusinessTypeEnum;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.request.PushConfigurationRequestDTO;
import cn.bmilk.tools.utils.GsonUtils;
import org.junit.Test;

import java.util.*;

public class YamlUtilsTest {

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
