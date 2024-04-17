package cn.bmilk.amp.ampService.task;

import cn.bmilk.amp.ampService.config.SpringBeanUtils;
import cn.bmilk.amp.ampService.service.AmpService;

public class ConfigPushTask implements Runnable{

    private long id;

    private AmpService ampService;

    public ConfigPushTask(long id) {
        this.id = id;
        ampService = SpringBeanUtils.getBean("ampService", AmpService.class);
    }


    @Override
    public void run() {
        ampService.push(id);
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
