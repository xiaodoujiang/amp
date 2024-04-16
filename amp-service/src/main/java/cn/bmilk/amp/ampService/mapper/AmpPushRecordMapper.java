package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpApplicationEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemEntity;
import cn.bmilk.amp.ampService.mapper.entity.AmpPushRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpPushRecordMapper {

    /**
     * 批量插入
     * @param ampPushRecordEntityList
     */
    void batchInsert(@Param("entityList")List<AmpPushRecordEntity> ampPushRecordEntityList);

    /**
     * 插入
     * @param ampPushRecordEntity
     */
    void insert(@Param("entity") AmpPushRecordEntity ampPushRecordEntity);

    /**
     * 根据单号查询列表
     * @param ampNo
     * @return
     */
    List<AmpPushRecordEntity> queryByAmpNo(@Param("ampNo") String ampNo);

    /**
     * 查询推送详情
     * @param id
     * @return
     */
    AmpPushRecordEntity queryById(@Param("id") long id);
}
