CREATE TABLE `AMP_CONFIG_ITEM_HIS` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    `CREATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '创建人',
    `UPDATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '最后一次更新人',
    `UNIQUE_TOKEN` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，其他-删除',
    `AMP_CONFIG_ITEM_ID` BIGINT(20) NOT NULL COMMENT '配置表ID',
    `UPDATE_USER` VARCHAR(32) NOT NULL COMMENT '变更人',
    `UPDATE_DATE` DATETIME NOT NULL COMMENT '变更时间',
    `CONFIG_KEY` VARCHAR(512) NOT NULL COMMENT '配置项KEY',
    `CONFIG_VALUE` VARCHAR(4000) NOT NULL COMMENT '配置项VALUE',
    `CONFIG_TYPE` VARCHAR(64) NOT NULL COMMENT '配置项类型',
    `CONFIG_DESC` VARCHAR(256) NOT NULL COMMENT '描述',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `AMP_APP_ENV_CONFIG` (`AMP_NO`,`APPLICATION_NAME`,`ENVIRONMENT_NAME`,`CONFIG_KEY`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='配置项变更历史表';
