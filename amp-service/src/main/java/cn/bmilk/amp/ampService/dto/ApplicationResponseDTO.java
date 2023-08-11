package cn.bmilk.amp.ampService.dto;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationResponseDTO {

    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 应用描述
     */
    private String applicationDesc;

    public static List<ApplicationResponseDTO> buildApplicationResponseDTOList(List<AmpApplicationEntity> ampApplicationEntityList){
        List<ApplicationResponseDTO> applicationResponseDTOList = new ArrayList<ApplicationResponseDTO>();
        for (AmpApplicationEntity ampApplicationEntity : ampApplicationEntityList){
            applicationResponseDTOList.add(buildApplicationResponseDTO(ampApplicationEntity));
        }
        return applicationResponseDTOList;
    }

    public static ApplicationResponseDTO buildApplicationResponseDTO(AmpApplicationEntity ampApplicationEntity){
        ApplicationResponseDTO ApplicationResponseDTO = new ApplicationResponseDTO();
        ApplicationResponseDTO.setApplicationName(ampApplicationEntity.getApplicationName());
        ApplicationResponseDTO.setApplicationDesc(ampApplicationEntity.getApplicationDesc());
        return ApplicationResponseDTO;
    }
}
