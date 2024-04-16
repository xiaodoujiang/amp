package cn.bmilk.amp.ampService.controller;

import cn.bmilk.amp.ampService.common.StatusEnum;
import cn.bmilk.amp.ampService.dto.request.AmpPushRequestDTO;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AmpPushResponseDTO;
import cn.bmilk.amp.ampService.dto.response.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.dto.response.BaseResponseDTO;
import cn.bmilk.amp.ampService.service.AmpService;

import cn.bmilk.amp.gwcommon.response.ResponseCodeEnum;
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
    public BaseResponseDTO createAmp(@RequestBody AmpRecordRequestDTO recordRequestDTO) {
        BaseResponseDTO responseDTO = null;
        String verify = verifyCreateAmp(recordRequestDTO);
        if (StringUtils.isNoneBlank(verifyCreateAmp(recordRequestDTO))) {
            return BaseResponseDTO.FAILURE(ResponseCodeEnum.PARAMS_ERROR, verify);
        }
        try {
            List<AmpRecordResponseDTO> ampRecordResponseDTOList = ampService.createAmp(recordRequestDTO);
            responseDTO = BaseResponseDTO.SUCCESS(ampRecordResponseDTOList);
        } catch (Exception e) {
            BaseResponseDTO.FAILURE(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }
        return responseDTO;
    }

    @GetMapping("/ampNo")
    public BaseResponseDTO queryAmpRecord(@RequestParam(value = "ampNo") String ampNo) {
        AmpRecordResponseDTO responseDTO = ampService.queryAmpRecord(ampNo);
        return BaseResponseDTO.SUCCESS(responseDTO);
    }

    @GetMapping("/list")
    public BaseResponseDTO queryAmpRecordList(@RequestParam(value = "user", required = false) String createUser,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                              @RequestParam(name = "pageNo", required = false, defaultValue = "1") int pageNo) {
        List<AmpRecordResponseDTO> responseDTO = ampService.queryAmpRecordList(createUser, pageSize, pageNo);
        return BaseResponseDTO.SUCCESS(responseDTO);
    }

    @PostMapping("/delete")
    public BaseResponseDTO deleteAmpRecord(@RequestParam(value = "ampNo") String ampNo) {
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        try {
            ampService.deleteAmpRecord(ampNo);
            baseResponseDTO.setStatus(StatusEnum.SUCCESS.name());
            baseResponseDTO.setErrCode(ResponseCodeEnum.SUCCESS.getCode());
            baseResponseDTO.setErrMsg(ResponseCodeEnum.SUCCESS.getMsg());
        }catch (Exception e){
            baseResponseDTO.setStatus(StatusEnum.FAILURE.name());
            baseResponseDTO.setErrCode(ResponseCodeEnum.SYSTEM_ERROR.getCode());
            baseResponseDTO.setErrMsg(e.getMessage());
        }
        return baseResponseDTO;
    }

    @PostMapping("/push")
    public BaseResponseDTO push(@RequestBody AmpPushRequestDTO ampPushRequestDTO){
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
        try {
            String verify = verifyPush(ampPushRequestDTO);
            if(StringUtils.isBlank(verify)){
                return BaseResponseDTO.FAILURE(ResponseCodeEnum.PARAMS_ERROR, verify);
            }
            AmpPushResponseDTO ampPushResponseDTO = ampService.recordPush(ampPushRequestDTO);
            baseResponseDTO = BaseResponseDTO.SUCCESS(ampPushResponseDTO);
        }catch (Exception e){
            baseResponseDTO = BaseResponseDTO.FAILURE(ResponseCodeEnum.SYSTEM_ERROR, e.getMessage());
        }
        return baseResponseDTO;

    }


    private String verifyCreateAmp(AmpRecordRequestDTO recordRequestDTO) {
        if (null == recordRequestDTO) {
            return "requestDTO is null";
        }
        StringBuilder sb = new StringBuilder();
        if (null == recordRequestDTO.getEnvironmentList() || recordRequestDTO.getEnvironmentList().isEmpty()) {
            sb.append("EnvironmentList is null");
        }
        if (null == recordRequestDTO.getApplicationId()) {
            sb.append("ApplicationId is null");
        }
        if (StringUtils.isBlank(recordRequestDTO.getCreateUser())) {
            sb.append("CreateUser is null");
        }
        if (null == recordRequestDTO.getEnvConfigMap() || recordRequestDTO.getEnvConfigMap().isEmpty()) {
            sb.append("EnvConfigMap is null");
        }
        if (recordRequestDTO.getEnvironmentList().size() != recordRequestDTO.getEnvConfigMap().size()) {
            sb.append("env and envConfig not match");
        }
        for (String env : recordRequestDTO.getEnvironmentList()) {
            if (!recordRequestDTO.getEnvConfigMap().containsKey(env)) {
                sb.append("env and envConfig not match");
            }
        }
        return sb.toString();
    }

    private String verifyPush(AmpPushRequestDTO ampPushRequestDTO){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isBlank(ampPushRequestDTO.getAmpNo())){
            sb.append("ampNo is null;");
        }
        return sb.toString();
    }
}
