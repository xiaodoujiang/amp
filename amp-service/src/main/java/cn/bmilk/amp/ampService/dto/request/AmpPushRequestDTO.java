package cn.bmilk.amp.ampService.dto.request;

import cn.bmilk.amp.ampService.dto.ConfigDetailDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AmpPushRequestDTO {
    /**
     * ampNo
     */
    private String ampNo;

    /**
     * 环境列表
     */
    private List<String> colonyList;

}
