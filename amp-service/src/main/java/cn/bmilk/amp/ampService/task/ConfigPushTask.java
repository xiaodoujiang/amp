package cn.bmilk.amp.ampService.task;

import cn.bmilk.amp.ampService.config.SpringBeanUtils;
import cn.bmilk.amp.ampService.service.AmpService;
import cn.bmilk.amp.ampService.service.AppConfigService;

public class ConfigPushTask implements Runnable{

    private long id;

    private AppConfigService appConfigService;

    public ConfigPushTask(long id) {
        this.id = id;
        appConfigService = SpringBeanUtils.getBean("appConfigService", AppConfigService.class);
    }


    @Override
    public void run() {
        appConfigService.push(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if(obj instanceof ConfigPushTask){
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new Long(id).hashCode();
    }
}
