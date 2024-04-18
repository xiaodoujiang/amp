package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpConfigItemMapper {

    /**
     * 擦汗如一条配置信息
     * @param ampConfigItemEntity
     */
    void insert(@Param("entity") AmpConfigItemEntity ampConfigItemEntity);

    /**
     * 批量插入
     * @param ampConfigItemEntityList
     */
    void batchInsert(@Param("entityList")List<AmpConfigItemEntity> ampConfigItemEntityList);

    /**
     * 查询指定应用的所有配置项
     * @param appName
     * @return
     */
    List<AmpConfigItemEntity> queryByAppNameAndEnv(@Param("appName")String appName,
                                                   @Param("env") String env);


}
