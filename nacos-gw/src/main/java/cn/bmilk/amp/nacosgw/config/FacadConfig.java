package cn.bmilk.amp.nacosgw.config;

import cn.bmilk.tools.http.BaseJerseyFacade;
import cn.bmilk.tools.http.JerseyClientFactory;
import cn.bmilk.tools.http.JerseyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;


@Component
public class FacadConfig {

    @Bean("nacosJerseyFacade")
    public BaseJerseyFacade getNacosFaced(){
        Client client = JerseyClientFactory.builder(new JerseyProperties());
        return BaseJerseyFacade.builder(client);
    }
}
