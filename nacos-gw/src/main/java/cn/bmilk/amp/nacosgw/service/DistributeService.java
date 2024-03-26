package cn.bmilk.amp.nacosgw.service;

import cn.bmilk.amp.gwcommon.request.BaseRequestDTO;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DistributeService {

    @Resource
    private Map<String, AbstractGwService<? extends BaseRequestDTO>> gwServiceMap;

    public BaseResponseDTO distribute(GwRequestDTO gwRequestDTO){
        AbstractGwService<? extends BaseRequestDTO> abstractGwService = gwServiceMap.get(gwRequestDTO.getBusinessType());
        if(null == abstractGwService){
            throw new IllegalArgumentException(String.format("can not find service for give businessType, businessType[%s]",
                    gwRequestDTO.getBusinessType()));
        }
        return abstractGwService.transaction(gwRequestDTO);
    }
}
