package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpAppEnvRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpAppColonyRelMapper {

    /**
     * 插入应用环境关联关系
     * @param ampAppEnvRelEntity
     */
    void insert(@Param("entity")AmpAppEnvRelEntity ampAppEnvRelEntity);

    /**
     * 根据app名称查询集群绑定关系
     * @param appName
     * @return
     */
    List<AmpAppEnvRelEntity> queryByApp(@Param("entity") String appName);
}
