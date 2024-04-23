CREATE TABLE `AMP_APP_COLONY_REL` (
    `ID` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `CREATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `UPDATE_TIME` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    `CREATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '创建人',
    `UPDATE_AT` VARCHAR(32) NOT NULL DEFAULT 'SYS' COMMENT '最后一次更新人',
    `UNIQUE_TOKEN` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，其他-删除',
    `APP_NAME` VARCHAR(64) NOT NULL COMMENT '应用名称',
    `COLONY_NAME` VARCHAR(64) NOT NULL COMMENT '集群名称',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `UK_ALL_NAME` (`APP_NAME`,`COLONY_NAME`,`UNIQUE_TOKEN`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用部署集群表';