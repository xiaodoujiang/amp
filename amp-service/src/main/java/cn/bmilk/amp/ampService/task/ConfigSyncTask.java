package cn.bmilk.amp.ampService.task;

import cn.bmilk.amp.ampService.config.SpringBeanUtils;
import cn.bmilk.amp.ampService.service.AppConfigService;

public class ConfigSyncTask  implements Runnable{

    private long id;

    private AppConfigService appConfigService;

    public ConfigSyncTask(long id) {
        this.id = id;
        appConfigService = SpringBeanUtils.getBean("appConfigService", AppConfigService.class);
    }

    @Override
    public void run() {
        appConfigService.syncConfig(id);
    }
}
