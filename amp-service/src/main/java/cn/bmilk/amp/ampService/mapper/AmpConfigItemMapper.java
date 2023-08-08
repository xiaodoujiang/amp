package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpConfigItemMapper {

    /**
     * 擦汗如一条配置信息
     * @param ampConfigItemEntity
     */
    void insert(@Param("entity") AmpConfigItemEntity ampConfigItemEntity);


}
