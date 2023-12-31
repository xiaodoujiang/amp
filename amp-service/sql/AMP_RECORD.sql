CREATE TABLE `AMP_RECORD` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    `CREATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '创建人',
    `UPDATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '最后一次更新人',
    `UNIQUE_TOKEN` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，其他-删除',
    `AMP_NO` VARCHAR(64) NOT NULL COMMENT '项目编号',
    `ENVIRONMENT_LIST` VARCHAR(256) NOT NULL COMMENT '环境列表，","隔开',
    `APPLICATION_ID` BIGINT(20) NOT NULL COMMENT '应用ID',
    `AMP_DESC` VARCHAR(128) COMMENT '项目描述',
    `AMP_TASK_REL` VARCHAR(4000) COMMENT '关联TASK地址',
    `LAUNCH_DATE` DATETIME COMMENT '预计上线日期',
    `CREATE_USER` VARCHAR(64) NOT NULL COMMENT '创建人',
    `STATUS` VARCHAR(32) NOT NULL COMMENT 'AMP单状态',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_AMP_NO` (`AMP_NO`,`UNIQUE_TOKEN`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='amp记录表';