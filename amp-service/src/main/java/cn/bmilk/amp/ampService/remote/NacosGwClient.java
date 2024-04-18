package cn.bmilk.amp.ampService.remote;

import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "nacos-gw")
public interface NacosGwClient {

    @PostMapping("/gw/distribute")
    String distribute(GwRequestDTO gwRequestDTO);

}
