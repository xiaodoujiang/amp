package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.dto.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.dto.ConfigResponseDTO;
import cn.bmilk.amp.ampService.service.AmpService;
import cn.bmilk.amp.response.CommonResp;

import cn.bmilk.amp.response.ResponseCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/amp")
public class AmpController {
    @Resource
    private AmpService ampService;

    @PostMapping("/create")
    public CommonResp<AmpRecordResponseDTO> createAmp(@RequestBody AmpRecordRequestDTO recordRequestDTO){
        String verify = verifyCreateAmp(recordRequestDTO);
        if(StringUtils.isNoneBlank(verifyCreateAmp(recordRequestDTO))){
            return CommonResp.FAILURE(ResponseCodeEnum.PARAMS_ERROR, verify);
        }

        AmpRecordResponseDTO responseDTO = ampService.createAmp(recordRequestDTO);

        return CommonResp.SUCCESS(responseDTO);
    }

    @GetMapping("/param")
    public AmpRecordRequestDTO get(){
        AmpRecordRequestDTO ampRecordRequestDTO = new AmpRecordRequestDTO();
        ampRecordRequestDTO.setApplicationId(1L);
        ampRecordRequestDTO.setAmpDesc("测试");
        ampRecordRequestDTO.setAmpTaskRel("www.test.com");
        ampRecordRequestDTO.setCreateUser("test");
        ampRecordRequestDTO.setLaunchDate(new Date());
        List<String> envList = new ArrayList<>();
        envList.add("test");
        envList.add("dev");
        ampRecordRequestDTO.setEnvironmentList(envList);
        ampRecordRequestDTO.setMulEnvConfigConsistent(true);
        Map<String, List<ConfigResponseDTO>> envConfigMap = new HashMap<>();
        List<ConfigResponseDTO> configResponseDTOList = new ArrayList<>();
        ConfigResponseDTO configResponseDTO = new ConfigResponseDTO();
        configResponseDTO.setConfigDesc("db地址");
        configResponseDTO.setConfigKey("spring.datasource.url");
        configResponseDTO.setConfigValue("jdbc:mariadb://96.43.92.79:3306/amp?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
        configResponseDTOList.add(configResponseDTO);
        ConfigResponseDTO configResponseDTO2 = new ConfigResponseDTO();
        configResponseDTO2.setConfigDesc("db驱动");
        configResponseDTO2.setConfigKey("spring.datasource.driver-class-name");
        configResponseDTO2.setConfigValue("org.mariadb.jdbc.Driver");
        configResponseDTOList.add(configResponseDTO2);
        ConfigResponseDTO configResponseDTO3 = new ConfigResponseDTO();
        configResponseDTO3.setConfigDesc("实体类包路径");
        configResponseDTO3.setConfigKey("mybatis.type-aliases-package");
        configResponseDTO3.setConfigValue("cn.bmilk.amp.ampService.mapper.entity");
        configResponseDTOList.add(configResponseDTO3);
        envConfigMap.put("dev", configResponseDTOList);
        ampRecordRequestDTO.setEnvConfigMap(envConfigMap);
        return ampRecordRequestDTO;



    }


    private String verifyCreateAmp(AmpRecordRequestDTO recordRequestDTO){
        if(null == recordRequestDTO){
            return "requestDTO is null";
        }
        StringBuilder sb = new StringBuilder();
        if(null == recordRequestDTO.getEnvironmentList() || recordRequestDTO.getEnvironmentList().isEmpty()){
            sb.append("EnvironmentList is null");
        }
        if(null == recordRequestDTO.getApplicationId()){
            sb.append("ApplicationId is null");
        }
        if(StringUtils.isBlank(recordRequestDTO.getCreateUser())){
            sb.append("CreateUser is null");
        }
        if(null == recordRequestDTO.getEnvConfigMap() || recordRequestDTO.getEnvConfigMap().isEmpty()){
            sb.append("EnvConfigMap is null");
        }
        return sb.toString();
    }
}
