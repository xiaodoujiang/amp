package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpEnvironmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpEnvironmentMapper {

    /**
     * 插入环境信息
     * @param ampEnvironmentEntity
     */
    void insert(@Param("entity") AmpEnvironmentEntity ampEnvironmentEntity);

}
