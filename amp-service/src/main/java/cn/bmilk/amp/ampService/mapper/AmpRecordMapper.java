package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AmpRecordMapper {

    /**、
     * 插入一条amp记录
     * @param ampRecordEntity
     */
    void insert(@Param("entity") AmpRecordEntity ampRecordEntity);

}
