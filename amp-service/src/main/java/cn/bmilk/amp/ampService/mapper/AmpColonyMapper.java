package cn.bmilk.amp.ampService.mapper;

import cn.bmilk.amp.ampService.mapper.entity.AmpColonyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AmpColonyMapper {

    /**
     * 插入集群信息
     *
     * @param ampColonyEntity
     */
    void insert(@Param("entity") AmpColonyEntity ampColonyEntity);

    /**
     * 查询环境对应的集群列表
     */
    List<AmpColonyEntity> queryListByEnv(@Param("env") String env);


}
