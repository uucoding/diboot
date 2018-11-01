SET FOREIGN_KEY_CHECKS=0;

-- 消息表
-- DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(50) NOT NULL DEFAULT 'SMS' COMMENT '消息类型',
  `tmpl_id` int(11) NOT NULL DEFAULT '0' COMMENT '模板id',
  `business_type` varchar(50) DEFAULT NULL COMMENT '关联业务类型',
  `business_id` int(11) DEFAULT NULL COMMENT '关联业务ID',
  `sender` varchar(50) DEFAULT NULL COMMENT '发送人',
  `receiver` varchar(50) NOT NULL COMMENT '接收人',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(512) NOT NULL COMMENT '内容',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `status` varchar(20) NOT NULL DEFAULT 'NEW' COMMENT '发送状态',
  `schedule_time` timestamp NULL DEFAULT NULL COMMENT '计划时间',
  `response` varchar(50) DEFAULT NULL COMMENT '发送结果',
  `extdata` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '有效',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_message` (`business_type`,`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='消息';

-- 消息模板
-- DROP TABLE IF EXISTS `message_tmpl`;
CREATE TABLE `message_tmpl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(50) NOT NULL DEFAULT 'SMS' COMMENT '类型',
  `code` varchar(32) NOT NULL COMMENT '模板编码',
  `msg_type` varchar(50) DEFAULT NULL COMMENT '消息类型',
  `business_type` varchar(50) DEFAULT NULL COMMENT '适用业务类型',
  `business_id` int(11) DEFAULT NULL COMMENT '适用业务ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `msg_title` varchar(255) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(512) NOT NULL COMMENT '内容',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `extdata` varchar(1024) DEFAULT NULL COMMENT '扩展数据',
  `system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '系统模板',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '有效',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_message_tmpl` (`code`,`business_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='消息模板';
