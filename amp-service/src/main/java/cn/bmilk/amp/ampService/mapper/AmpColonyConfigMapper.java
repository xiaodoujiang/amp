package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpColonyConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AmpColonyConfigMapper {
    /**
     * 插入
     *
     * @param entity
     */
    void insert(@Param("entity") AmpColonyConfigEntity entity);

    /**
     * 批量插入
     * @param colonyConfigEntityList
     */
    void batchInsert(@Param("entityList") List<AmpColonyConfigEntity> colonyConfigEntityList);

    /**
     * 根据key列表查询环境配置项
     * @param keySet
     * @param colonyName
     * @return
     */
    List<AmpColonyConfigEntity> queryByKeyListAndColony(@Param("keySet") Set<String> keySet,
                                                        @Param("colonyName") String colonyName);

    /**
     * 查询某一用用的所有环境配置
     * @param appName
     * @param colonyName
     * @return
     */
    List<AmpColonyConfigEntity> queryByAppAndColony(@Param("appName") String appName,
                                                    @Param("colonyName") String colonyName);


}
