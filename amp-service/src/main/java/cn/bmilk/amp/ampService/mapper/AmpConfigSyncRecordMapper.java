package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigSyncRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpConfigSyncRecordMapper {

    /**
     * 批量插入
     * @param entityList
     */
    void batchInsert(@Param("entityList")List<AmpConfigSyncRecordEntity> entityList);

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    AmpConfigSyncRecordEntity queryById(@Param("id") long id);

    /**
     * 更新状态
     * @param newStatus
     * @param oldStatus
     * @param id
     */
    void updateStatus(@Param("newStatus") String newStatus,
                      @Param("oldStatus") String oldStatus,
                      @Param("id") long id);
}
