package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpApplicationMapper {

    /**
     * 插入应用信息
     * @param ampApplicationEntity 应用信息 {@link AmpApplicationEntity}
     */
    void insert(@Param("entity") AmpApplicationEntity ampApplicationEntity);

    /**
     * 查询所有应用
     * @return 应用列表 {@link AmpApplicationEntity}
     */
    List<AmpApplicationEntity> queryAllApplication();

    /**
     * 查询应用详情
     * @param appName
     * @return
     */
    AmpApplicationEntity queryByAppName(@Param("appName") String appName);


}
