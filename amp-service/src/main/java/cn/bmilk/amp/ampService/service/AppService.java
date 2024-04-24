package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.AppColonyBindDTO;
import cn.bmilk.amp.ampService.dto.AppColonyConfigDTO;
import cn.bmilk.amp.ampService.dto.request.ApplicationRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpAppColonyRelEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpColonyConfigEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    @Resource
    private AmpApplicationMapper ampApplicationMapper;
    @Resource
    private AppTransactionalService appTransactionalService;

    public AppDetailResponseDTO queryAppDetail(String appName){
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public AppDetailResponseDTO createApp(ApplicationRequestDTO requestDTO){
        AmpApplicationEntity ampApplicationEntity = AmpApplicationEntity.build(requestDTO);
        ampApplicationMapper.insert(ampApplicationEntity);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }

    public void bindAppColony(ApplicationRequestDTO requestDTO){
        List<AmpAppColonyRelEntity> appColonyRelEntityList = new ArrayList<>();

        List<AmpColonyConfigEntity> colonyConfigEntityList = new ArrayList<>();

        for (AppColonyBindDTO appColonyBindDTO : requestDTO.getAppColonyBindDTOList()){
            AmpAppColonyRelEntity ampAppColonyRelEntity = AmpAppColonyRelEntity.build(appColonyBindDTO);
            appColonyRelEntityList.add(ampAppColonyRelEntity);
            for (AppColonyConfigDTO appColonyConfigDTO : appColonyBindDTO.getAppColonyConfigDTOList()){
                AmpColonyConfigEntity ampColonyConfigEntity = AmpColonyConfigEntity.build(appColonyConfigDTO, appColonyBindDTO);
                colonyConfigEntityList.add(ampColonyConfigEntity);
            }
        }
        appTransactionalService.bindAppColony(appColonyRelEntityList, colonyConfigEntityList);
    }



}
