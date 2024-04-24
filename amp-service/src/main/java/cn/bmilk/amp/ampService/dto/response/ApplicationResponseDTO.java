package cn.bmilk.amp.ampService.dto.response;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationResponseDTO {

    /**
     * 应用名称
     */
    private String appName;
    /**
     * 应用描述
     */
    private String appDesc;

    private List<ConfigDetailDTO> configDetailDTOList;

    public static List<ApplicationResponseDTO> buildApplicationResponseDTOList(List<AmpApplicationEntity> ampApplicationEntityList){
        List<ApplicationResponseDTO> applicationResponseDTOList = new ArrayList<ApplicationResponseDTO>();
        for (AmpApplicationEntity ampApplicationEntity : ampApplicationEntityList){
            applicationResponseDTOList.add(buildApplicationResponseDTO(ampApplicationEntity));
        }
        return applicationResponseDTOList;
    }

    public static ApplicationResponseDTO buildApplicationResponseDTO(AmpApplicationEntity ampApplicationEntity){
        ApplicationResponseDTO ApplicationResponseDTO = new ApplicationResponseDTO();
        ApplicationResponseDTO.setAppName(ampApplicationEntity.getAppName());
        ApplicationResponseDTO.setAppDesc(ampApplicationEntity.getAppDesc());
        return ApplicationResponseDTO;
    }


}
