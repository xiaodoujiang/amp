package cn.bmilk.amp.gwcommon.response;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseDTO implements Serializable {

    private String status;

    private String errCode;

    private String errMsg;
}
