package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpColonyConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpColonyConfigMapper {

    /**
     * 插入集群配置信息
     * @param ampColonyConfigEntity
     */
    void insert(@Param("entity") AmpColonyConfigEntity ampColonyConfigEntity);


}
