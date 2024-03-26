package cn.bmilk.amp.nacosgw.remote;

import lombok.Data;

@Data
public class NacosBaseResponseDTO<T>{

    private Integer code;

    private String message;

    private  T data;

    public boolean isSuccess(){
        return null != code && 0 == code;
    }

}
