CREATE TABLE `AMP_COLONY_CONFIG` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    `CREATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '创建人',
    `UPDATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '最后一次更新人',
    `UNIQUE_TOKEN` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，其他-删除',
    `COLONY_ID` BIGINT(20) NOT NULL COMMENT '集群ID',
    `CONFIG_CENTER_TYPE` VARCHAR(32) NOT NULL COMMENT '配置中心类型',
    `CONFIG_CENTER_ADDRESS` VARCHAR(512) NOT NULL COMMENT '配置中心地址',
    `CONFIG_CENTER_USERNAME` VARCHAR(64) COMMENT '配置中心访问用户名',
    `CONFIG_CENTER_PASSWORD` VARCHAR(128) COMMENT '配置中心密码',
    `CONFIG_CENTER_AUTH_KEY` VARCHAR(4000) COMMENT '配置中心密钥',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_COLONY_CONFIG_TYPE` (`COLONY_ID`,`CONFIG_CENTER_TYPE`,`UNIQUE_TOKEN`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='集群配置中心信息';