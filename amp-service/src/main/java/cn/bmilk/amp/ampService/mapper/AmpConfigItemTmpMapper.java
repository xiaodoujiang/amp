package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpConfigItemTmpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpConfigItemTmpMapper {

    /**
     * 批量插入临时配置
     */
    void batchInsert(@Param("entityList") List<AmpConfigItemTmpEntity> entityList);

    /**
     * 查询配置列表
     * @param ampNo amp单号
     * @return {@link AmpConfigItemTmpEntity}
     */
    List<AmpConfigItemTmpEntity> queryConfigListByAmpNo(@Param("ampNo") String ampNo);

    /**
     * 删除配置项根据ampNo
     * @param ampNo
     */
    void deleteConfigByAmpNo(@Param("ampNo") String ampNo);

}
