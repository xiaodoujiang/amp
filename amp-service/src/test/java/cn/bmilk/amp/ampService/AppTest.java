package cn.bmilk.amp.ampService;

import cn.bmilk.amp.ampService.common.AppTypeEnum;
import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.AppColonyConfigDTO;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.tools.utils.GsonUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    @Test
    public void appBuild(){
        ApplicationRequestDTO result = new ApplicationRequestDTO();

        result.setAppName("TEST-1");
        result.setAppType(AppTypeEnum.BUSINESS_APP.name());
        result.setConfigCenterApp("nacos");
        result.setAppDesc("TEST-1");
        result.setConfigGroup("DEFAULT_GROUP");
        result.setConfigTenant("public");
        result.setOperator("test");
        result.setConfigFileType("yaml");

        AppColonyConfigDTO appColonyConfigDTO1 = new AppColonyConfigDTO();
        appColonyConfigDTO1.setConfigKey("TEST-1_REQUEST_ADDRESS");
        appColonyConfigDTO1.setConfigValue("www.test1-dev1.com");
        appColonyConfigDTO1.setConfigDesc("test1请求地址");
        AppColonyConfigDTO appColonyConfigDTO2 = new AppColonyConfigDTO();
        appColonyConfigDTO2.setConfigKey("TEST-1_REQUEST_PATH");
        appColonyConfigDTO2.setConfigValue("/test1");
        appColonyConfigDTO2.setConfigDesc("test1请求路径");
        AppColonyBindDTO appColonyBindDTO1 = new AppColonyBindDTO();
        appColonyBindDTO1.setColonyName("colony-dev-1");
        appColonyBindDTO1.setAppName("TEST-1");
        List<AppColonyConfigDTO> appColonyConfigDTOList = new ArrayList<>();
        appColonyConfigDTOList.add(appColonyConfigDTO1);
        appColonyConfigDTOList.add(appColonyConfigDTO2);
        appColonyBindDTO1.setAppColonyConfigDTOList(appColonyConfigDTOList);


        AppColonyConfigDTO appColonyConfigDTO3 = new AppColonyConfigDTO();
        appColonyConfigDTO3.setConfigKey("TEST-1_REQUEST_ADDRESS");
        appColonyConfigDTO3.setConfigValue("www.test1-test1.com");
        appColonyConfigDTO3.setConfigDesc("test1请求地址");
        AppColonyConfigDTO appColonyConfigDTO4 = new AppColonyConfigDTO();
        appColonyConfigDTO4.setConfigKey("TEST-1_REQUEST_PATH");
        appColonyConfigDTO4.setConfigValue("/test1");
        appColonyConfigDTO4.setConfigDesc("test1请求路径");
        AppColonyBindDTO appColonyBindDTO2 = new AppColonyBindDTO();
        appColonyBindDTO2.setColonyName("colony-test-1");
        appColonyBindDTO2.setAppName("TEST-1");
        List<AppColonyConfigDTO> appColonyConfigDTOList2 = new ArrayList<>();
        appColonyConfigDTOList2.add(appColonyConfigDTO1);
        appColonyConfigDTOList2.add(appColonyConfigDTO2);
        appColonyBindDTO2.setAppColonyConfigDTOList(appColonyConfigDTOList2);

        List<AppColonyBindDTO> appColonyBindDTOList = new ArrayList<>();
        appColonyBindDTOList.add(appColonyBindDTO1);
        appColonyBindDTOList.add(appColonyBindDTO2);

        result.setAppColonyBindDTOList(appColonyBindDTOList);


        System.out.println(GsonUtils.toJson(result));


    }
}
