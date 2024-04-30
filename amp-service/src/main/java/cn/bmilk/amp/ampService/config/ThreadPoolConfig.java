package cn.bmilk.amp.ampService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    private static final String PUSH_CONFIG_THREAD_NAME = "pushConfigExecutor";

    private static final String CONFIG_SYNC_THREAD_NAME = "configSyncExecutor";


    @Bean(name = PUSH_CONFIG_THREAD_NAME)
    public ThreadPoolTaskExecutor getPushExecutor(){
        ThreadPoolTaskExecutor result = new ThreadPoolTaskExecutor();
        result.setCorePoolSize(3);
        result.setMaxPoolSize(10);
        result.setAllowCoreThreadTimeOut(false);
        result.setQueueCapacity(1000);
        result.setThreadNamePrefix(PUSH_CONFIG_THREAD_NAME);
        return result;
    }


    @Bean(name = CONFIG_SYNC_THREAD_NAME)
    public ThreadPoolTaskExecutor getSyncExecutor(){
        ThreadPoolTaskExecutor result = new ThreadPoolTaskExecutor();
        result.setCorePoolSize(1);
        result.setMaxPoolSize(3);
        result.setAllowCoreThreadTimeOut(false);
        result.setQueueCapacity(100);
        result.setThreadNamePrefix(CONFIG_SYNC_THREAD_NAME);
        return result;
    }
}
