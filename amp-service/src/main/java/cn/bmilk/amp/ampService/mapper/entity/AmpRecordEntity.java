package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.common.AmpStatusEnum;
import cn.bmilk.amp.ampService.dto.AmpRecordRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmpRecordEntity extends BaseEntity {
    /**
     * 项目编号
     */
    private String ampNo;
    /**
     * 环境列表，','隔开
     */
    private String environmentList;
    /**
     * 应用id
     */
    private long applicationId;
    /**
     * 项目描述
     */
    private String ampDesc;
    /**
     * 关联Task地址
     */
    private String ampTaskRel;
    /**
     * 预计上线日期
     */
    private Date launchDate;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * amp单状态
     */
    private String status;
    /**
     * 类型
     */
    private String type;
    /**
     * 子单
     */
    private String childAmpList;
    /**
     * 推送人员
     */
    private String operator;


    public static AmpRecordEntity buildAmpRecordEntity(AmpRecordRequestDTO ampRecordRequestDTO,
                                                       String ampNo) {
        AmpRecordEntity result = new AmpRecordEntity();

        result.setAmpNo(ampNo);
        result.setEnvironmentList(ampRecordRequestDTO.getEnvironmentList().stream().map(String::valueOf).collect(Collectors.joining(",")));
        result.setApplicationId(ampRecordRequestDTO.getApplicationId());
        result.setAmpDesc(ampRecordRequestDTO.getAmpDesc());
        result.setAmpTaskRel(ampRecordRequestDTO.getAmpTaskRel());
        result.setLaunchDate(ampRecordRequestDTO.getLaunchDate());
        result.setCreateUser(ampRecordRequestDTO.getCreateUser());
        result.setStatus(AmpStatusEnum.NEW.name());
        result.setAmpTaskRel(ampRecordRequestDTO.getAmpTaskRel());
        return result;
    }
}
