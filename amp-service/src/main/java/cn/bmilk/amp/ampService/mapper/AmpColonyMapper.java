package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpColonyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpColonyMapper {

    /**
     * 插入集群信息
     * @param ampColonyEntity
     */
    void insert(@Param("entity") AmpColonyEntity ampColonyEntity);


}
