CREATE TABLE `AMP_APPLICATION` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    `CREATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '创建人',
    `UPDATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '最后一次更新人',
    `UNIQUE_TOKEN` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，其他-删除',
    `APPLICATION_NAME` VARCHAR(64) NOT NULL COMMENT '应用名称',
    `APPLICATION_DESC` VARCHAR(256) COMMENT '应用描述',
    `APPLICATION_TYPE` VARCHAR(32) NOT NULL DEFAULT 'BUSINESS' COMMENT '应用名称',
    `ADDRESS_ENV_KEY` VARCHAR(128) COMMENT '应用请求地址KEY',
    `ADDRESS_ENV_VALUE` VARCHAR(128) COMMENT '应用请求地址VALUE',
    `PATH_ENV_KEY` VARCHAR(128) COMMENT '应用URI KEY',
    `PATH_ENV_VALUE` VARCHAR(128) COMMENT '应用URI VALUE',
    `CONFIG_CENTER_TYPE` VARCHAR(32) NOT NULL COMMENT '使用的配置中心类型',
    `CONFIG_TENANT` VARCHAR(32) NOT NULL COMMENT '配置中心租户',
    `OPERATOR` VARCHAR(64) NOT NULL COMMENT '操作员',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_APPLICATION_NAME` (`APPLICATION_NAME`, `UNIQUE_TOKEN`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用表';