package cn.bmilk.amp.ampService.service;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
import cn.bmilk.amp.ampService.dto.response.AmpRecordResponseDTO;
import cn.bmilk.amp.ampService.mapper.AmpConfigItemTmpMapper;
import cn.bmilk.amp.ampService.mapper.AmpRecordMapper;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public interface AmpPushService {

    /**
     * 配置推送服务
     * @param ampNo
     * @return
     */
    boolean push(String ampNo);

}
