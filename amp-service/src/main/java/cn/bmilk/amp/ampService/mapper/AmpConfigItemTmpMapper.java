package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpConfigItemTmpMapper {

    /**
     * 插入一条临时配置
     * @param ampConfigItemTmpEntity
     */
    void insert(@Param("entity") AmpConfigItemTmpEntity ampConfigItemTmpEntity);


}
