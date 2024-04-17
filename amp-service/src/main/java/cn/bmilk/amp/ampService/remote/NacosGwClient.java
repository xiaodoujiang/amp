package cn.bmilk.amp.ampService.remote;

import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("nacos-gw")
public interface NacosGwClient {

    @PostMapping("/gw/distribute")
    <T> T distribute(GwRequestDTO gwRequestDTO);

}
