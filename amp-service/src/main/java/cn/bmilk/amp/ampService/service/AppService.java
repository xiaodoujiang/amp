package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.response.AppDetailResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpApplicationMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import org.apache.catalina.core.ApplicationMapping;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppService {

    @Resource
    private AmpApplicationMapper ampApplicationMapper;

    public AppDetailResponseDTO queryAppDetail(String appName){
        AmpApplicationEntity ampApplicationEntity = ampApplicationMapper.queryByAppName(appName);
        return AppDetailResponseDTO.build(ampApplicationEntity);
    }



}
