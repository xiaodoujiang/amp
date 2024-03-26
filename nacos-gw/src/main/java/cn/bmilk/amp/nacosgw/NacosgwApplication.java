package cn.bmilk.amp.nacosgw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class NacosgwApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosgwApplication.class, args);
    }
}
