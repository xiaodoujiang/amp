package cn.bmilk.amp.ampService;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
import cn.bmilk.tools.utils.GsonUtils;
import org.junit.Test;

import java.util.*;

public class ParamTest {

    @Test
    public void buildParam(){
        AmpRecordRequestDTO ampRecordRequestDTO = new AmpRecordRequestDTO();
        List<String> envList = new ArrayList<>();
        envList.add("dev");
        envList.add("test");
        ampRecordRequestDTO.setEnvironmentList(envList);
        ampRecordRequestDTO.setApplicationId(1L);
        ampRecordRequestDTO.setApplicationName("TEST-1");
        ampRecordRequestDTO.setAmpTaskRel("https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.32");
        ampRecordRequestDTO.setCreateUser("bmilk");
        ampRecordRequestDTO.setLaunchDate(new Date());
        Map<String, List<ConfigDetailDTO>> envConfigMap = new HashMap<>();
        List<ConfigDetailDTO> list = new ArrayList<>();
        for (int i =0; i<5 ;i++){
            ConfigDetailDTO configDetailDTO = new ConfigDetailDTO();
            configDetailDTO.setConfigKey("KEY-TEST-"+i);
            configDetailDTO.setConfigValue("value-test-" + i);
            configDetailDTO.setConfigType("NORMAL");
            configDetailDTO.setConfigDesc("config-test-"+i);
            list.add(configDetailDTO);
        }
        envConfigMap.put("dev", list);
        envConfigMap.put("test", list);
        ampRecordRequestDTO.setEnvConfigMap(envConfigMap);
        String json = GsonUtils.toJson(ampRecordRequestDTO);
        System.out.println(json);


    }
}
