package cn.bmilk.amp.nacosgw.service;

import cn.bmilk.amp.gwcommon.request.BaseRequestDTO;
import cn.bmilk.amp.gwcommon.request.GwRequestDTO;
import cn.bmilk.amp.gwcommon.response.BaseResponseDTO;
import cn.bmilk.tools.http.BaseJerseyFacade;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 *
 * @param <T>
 */
public abstract class AbstractGwService<T extends BaseRequestDTO> {

    public  BaseResponseDTO transaction(GwRequestDTO gwRequestDTO){
        T baseRequestDTO = parseBusinessRequestDTO(gwRequestDTO.getRequestDataJson());
        String errMsg = paramValid(baseRequestDTO);
        if(StringUtils.isNoneBlank(errMsg)){
            throw new IllegalArgumentException(errMsg);
        }
        return doTransaction(baseRequestDTO);
    }

    abstract protected BaseResponseDTO doTransaction(T requestDTO);

    abstract protected String paramValid(T requestDTO);

    abstract protected T parseBusinessRequestDTO(String requestDTOJson);

}
