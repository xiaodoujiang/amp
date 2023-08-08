package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemHisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpConfigItemHisMapper {

    /**
     * 插入配置操作历史
     * @param ampConfigItemHisEntity
     */
    void insert(@Param("entity") AmpConfigItemHisEntity ampConfigItemHisEntity);


}
