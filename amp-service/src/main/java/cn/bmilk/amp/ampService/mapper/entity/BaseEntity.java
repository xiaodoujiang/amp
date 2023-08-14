package cn.bmilk.amp.ampService.mapper.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createAt;
    /**
     * 最后一次更新人
     */
    private String updateAt;
    /**
     * 是否删除，0-未删除，其他-删除
     */
    private long uniqueToken;
}
