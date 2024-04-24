package cn.bmilk.amp.ampService.mapper.entity;

import cn.bmilk.amp.ampService.common.AmpStatusEnum;
import cn.bmilk.amp.ampService.dto.request.AmpRecordRequestDTO;
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
    private String environment;
    /**
     * 应用id
     */
    private long appId;
    /**
     * 应用名称
     */
    private String appName;
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
     * 推送人员
     */
    private String operator;


    public static AmpRecordEntity build(AmpRecordRequestDTO ampRecordRequestDTO, String ampNo, String env) {
        AmpRecordEntity ampRecordEntity = new AmpRecordEntity();

        ampRecordEntity.setAmpNo(ampNo);
        ampRecordEntity.setEnvironment(env);
        ampRecordEntity.setAppId(ampRecordRequestDTO.getAppId());
        ampRecordEntity.setAppName(ampRecordRequestDTO.getAppName());
        ampRecordEntity.setAmpDesc(ampRecordRequestDTO.getAmpDesc());
        ampRecordEntity.setAmpTaskRel(ampRecordRequestDTO.getAmpTaskRel());
        ampRecordEntity.setLaunchDate(ampRecordRequestDTO.getLaunchDate());
        ampRecordEntity.setCreateUser(ampRecordRequestDTO.getCreateUser());
        ampRecordEntity.setStatus(AmpStatusEnum.NEW.name());
        ampRecordEntity.setAmpTaskRel(ampRecordRequestDTO.getAmpTaskRel());
        ampRecordEntity.setType(ampRecordRequestDTO.getType());
        return ampRecordEntity;
    }
}
