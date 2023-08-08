package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpApplicationMapper {

    /**
     * 插入应用信息
     * @param ampApplicationEntity
     */
    void insert(@Param("entity") AmpApplicationEntity ampApplicationEntity);


}
