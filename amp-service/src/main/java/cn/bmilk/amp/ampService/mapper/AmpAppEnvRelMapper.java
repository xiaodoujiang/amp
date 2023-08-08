package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpAppEnvRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpAppEnvRelMapper {

    /**
     * 插入应用环境关联关系
     * @param ampAppEnvRelEntity
     */
    void insert(@Param("entity")AmpAppEnvRelEntity ampAppEnvRelEntity);
}
