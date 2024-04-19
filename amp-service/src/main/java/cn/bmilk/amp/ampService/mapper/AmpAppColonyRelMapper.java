package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpAppColonyRelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpAppColonyRelMapper {

    /**
     * 插入应用环境关联关系
     * @param ampAppColonyRelEntity
     */
    void insert(@Param("entity") AmpAppColonyRelEntity ampAppColonyRelEntity);

    /**
     * 根据app名称查询集群绑定关系
     * @param appName
     * @return
     */
    List<AmpAppColonyRelEntity> queryByAppName(@Param("appName") String appName);

    /**
     * 根据app名称查询集群绑定关系
     * @param appName
     * @return
     */
    List<AmpAppColonyRelEntity> queryByAppNameAndColony(@Param("appName") String appName,
                                                        @Param("colonyList") List<String> colonyList);

    /**
     * 查询app和
     * @param appName
     * @param colonyName
     * @return
     */
    AmpAppColonyRelEntity queryByAppAndColony(@Param("appName")String appName,
                                              @Param("colonyName") String colonyName);

    /**
     * 查询app和
     *
     * @param appNameList
     * @param colonyName
     * @return
     */
    List<AmpAppColonyRelEntity> queryByAppListAndColony(@Param("appNameList") List<String> appNameList,
                                                        @Param("colonyName") String colonyName);
}
