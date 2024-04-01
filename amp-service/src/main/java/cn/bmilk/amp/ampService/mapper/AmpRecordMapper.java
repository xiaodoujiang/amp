package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpRecordMapper {

    /**
     * 、
     * 插入一条amp记录
     *
     * @param ampRecordEntity
     */
    void insert(@Param("entity") AmpRecordEntity ampRecordEntity);

    /**
     * 根据amp单号查询amp详情
     *
     * @param ampNo amp单号
     * @return {@link AmpRecordEntity}
     */
    AmpRecordEntity queryAmpRecord(@Param("ampNo") String ampNo);

    /**
     * 查询指定用户创建的amp列表
     *
     * @param createUser 用户
     * @param offset 询偏移量
     * @param count  查询数量
     * @return {@link AmpRecordEntity}
     */
    List<AmpRecordEntity> queryAmpRecordList(@Param("createUser") String createUser,
                                             @Param("offset") int offset,
                                             @Param("count") int count);

}
