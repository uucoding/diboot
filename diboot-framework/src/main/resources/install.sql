SET FOREIGN_KEY_CHECKS=0;

-- 菜单表
-- DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(10) DEFAULT NULL COMMENT '上级菜单ID',
  `type` varchar(50) NOT NULL DEFAULT 'PC',
  `application` varchar(20) NOT NULL DEFAULT 'MS' COMMENT '应用',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `link` varchar(255) DEFAULT NULL COMMENT '链接',
  `sort_id` smallint(4) DEFAULT '999',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

INSERT INTO `menu` VALUES ('90', '0', 'PC', 'MS', '系统管理', 'fa fa-cogs', null, '90');
INSERT INTO `menu` VALUES ('91', '90', 'PC', 'MS', '系统用户管理', 'fa fa-users', '/user/', '91');
INSERT INTO `menu` VALUES ('92', '90', 'PC', 'MS', '角色权限管理', 'fa fa-unlock-alt', '/rolemenu/', '92');
INSERT INTO `menu` VALUES ('93', '90', 'PC', 'MS', '操作日志管理', 'fa fa-mouse-pointer', '/operationLog/', '93');
INSERT INTO `menu` VALUES ('94', '90', 'PC', 'MS', '元数据管理', 'fa fa-th', '/metadata/', '94');
-- INSERT INTO `menu` VALUES ('95', '90', 'PC', 'MS', '消息记录管理', 'fa fa-envelope-o', '/msg/message/', '95');
-- INSERT INTO `menu` VALUES ('96', '90', 'PC', 'MS', '消息模板管理', 'fa fa-clone', '/msg/messageTmpl/', '96');

-- 元数据表
-- DROP TABLE IF EXISTS `metadata`;
CREATE TABLE `metadata` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(10) NOT NULL COMMENT '父ID',
  `type` varchar(50) NOT NULL COMMENT '元数据类型',
  `item_name` varchar(255) NOT NULL COMMENT '元数据项名称',
  `item_value` varchar(255) DEFAULT NULL COMMENT '元数据项编码',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort_id` smallint(5) NOT NULL DEFAULT '1' COMMENT '排序号',
  `system` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是系统',
  `editable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可编辑',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='元数据';

INSERT INTO `metadata` VALUES ('100', '0', 'ROLE', '系统用户角色', null, '系统登录用户的角色定义元数据', '1', '1', '0', '1', '0', '2016-08-24 21:45:20');
INSERT INTO `metadata` VALUES ('101', '100', 'ROLE', '系统管理员', 'ADMIN', '所有权限', '1', '1', '1', '1', '0', '2016-08-24 21:45:21');
INSERT INTO `metadata` VALUES ('102', '100', 'ROLE', '业务操作员', 'USER', '部分权限', '1', '1', '1', '1', '0', '2016-08-24 21:45:21');
INSERT INTO `metadata` VALUES ('120', '0', 'GENDER', '性别', null, '', '1', '0', '1', '1', '10001', '2017-11-22 14:33:57');
INSERT INTO `metadata` VALUES ('121', '120', 'GENDER', '男', 'M', null, '1', '0', '1', '1', '0', '2017-11-22 14:33:57');
INSERT INTO `metadata` VALUES ('122', '120', 'GENDER', '女', 'F', null, '1', '0', '1', '1', '0', '2017-11-22 14:33:57');
INSERT INTO `metadata` VALUES ('130', '0', 'POSITION', '职位', null, '职位列表', '1', '1', '1', '1', null, '2018-05-30 11:56:10');
INSERT INTO `metadata` VALUES ('131', '130', 'POSITION', '部门经理', 'DM', null, '1', '1', '1', '1', null, '2018-05-30 11:56:39');
INSERT INTO `metadata` VALUES ('132', '130', 'POSITION', '销售', 'SALES', null, '1', '1', '1', '1', null, '2018-05-30 11:57:09');
INSERT INTO `metadata` VALUES ('200', '0', 'USER_STATUS', '员工状态', null, '描述员工状态的元数据', '1', '1', '1', '1', '0', '2016-08-24 21:45:20');
INSERT INTO `metadata` VALUES ('201', '109', 'USER_STATUS', '在职', 'A', null, '1', '1', '1', '1', '0', '2016-08-24 21:45:21');
INSERT INTO `metadata` VALUES ('202', '109', 'USER_STATUS', '离职', 'I', null, '1', '1', '1', '1', '0', '2016-08-24 21:46:13');

-- 认证用户
-- DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_type` varchar(64) NOT NULL COMMENT '用户类型',
  `user_id` bigint(11) unsigned NOT NULL COMMENT '用户',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `wechat` varchar(100) DEFAULT NULL COMMENT '微信',
  `openid` varchar(32) DEFAULT NULL COMMENT 'openid',
  `extdata` varchar(100) DEFAULT NULL COMMENT '扩展JSON',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='用户认证';

-- excel导入列对应关系
-- DROP TABLE IF EXISTS `excel_column`;
CREATE TABLE `excel_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_class` varchar(50) NOT NULL COMMENT 'Java对象类',
  `model_field` varchar(50) NOT NULL COMMENT 'Java对象属性',
  `col_name` varchar(50) NOT NULL COMMENT '列标题',
  `col_index` int(5) NOT NULL COMMENT '列索引',
  `data_type` varchar(20) DEFAULT NULL COMMENT '数据类型',
  `validation` varchar(50) DEFAULT NULL COMMENT '校验',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='excel列定义';

-- excel导入文件与数据的对应关系
DROP TABLE IF EXISTS `excel_import_record`;
CREATE TABLE `excel_import_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `file_uuid` varchar(32) DEFAULT NULL COMMENT '文件ID',
  `rel_obj_type` varchar(100) DEFAULT NULL COMMENT '关联类型',
  `rel_obj_id` bigint(18) unsigned DEFAULT NULL COMMENT '关联ID',
  `rel_obj_uid` varchar(32) DEFAULT NULL COMMENT '关联UUID',
  `extdata` varchar(100) DEFAULT NULL COMMENT '扩展JSON',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='导入记录';

-- 上传文件表
-- DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `uuid` varchar(32) NOT NULL COMMENT '编号',
  `rel_obj_type` varchar(50) DEFAULT NULL COMMENT '关联对象类型',
  `rel_obj_id` bigint(18) DEFAULT NULL COMMENT '关联对象ID',
  `name` varchar(100) NOT NULL COMMENT '文件名',
  `link` varchar(255) DEFAULT NULL COMMENT '文件链接',
  `path` varchar(255) NOT NULL COMMENT '路径',
  `file_type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `data_count` smallint(5) DEFAULT '0' COMMENT '数据量',
  `size` int(11) DEFAULT NULL COMMENT '大小',
  `status` char(1) NOT NULL DEFAULT 'S' COMMENT '保存状态',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `extdata` varchar(255) DEFAULT NULL COMMENT '扩展属性',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '有效性',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件记录';

-- 操作日志 
-- DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `source` varchar(16) NOT NULL COMMENT '来源应用',
  `user_type` varchar(16) NOT NULL DEFAULT 'User' COMMENT '用户类型',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `operation` varchar(32) NOT NULL COMMENT '操作',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求URL',
  `comment` varchar(255) DEFAULT NULL COMMENT '系统备注',
  `rel_obj_type` varchar(32) DEFAULT NULL COMMENT '操作对象',
  `rel_obj_id` varchar(32) DEFAULT NULL COMMENT '操作对象ID',
  `rel_obj_data` varchar(512) DEFAULT NULL COMMENT '关联数据',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '有效',
  `extdata` varchar(255) NOT NULL COMMENT '扩展',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_operate_log` (`rel_obj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- 角色菜单表
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role` varchar(50) NOT NULL COMMENT '角色',
  `menu_id` int(11) NOT NULL COMMENT '菜单'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单';

-- 角色菜单配置
INSERT INTO `role_menu` VALUES ('OPERATOR', '90');
INSERT INTO `role_menu` VALUES ('OPERATOR', '93');

-- 系统配置表
-- DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `rel_obj_type` varchar(100) NOT NULL COMMENT '关联对象',
  `rel_obj_id` bigint(11) unsigned NOT NULL COMMENT '关联数据',
  `category` varchar(100) NOT NULL COMMENT '类别',
  `subcategory` varchar(100) DEFAULT NULL COMMENT '子类别',
  `extdata` varchar(1024) DEFAULT NULL COMMENT '扩展JSON',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000015 DEFAULT CHARSET=utf8 COMMENT='系统配置参数';

-- 初始系统配置数据
INSERT INTO `system_config` (`rel_obj_type`,`rel_obj_id`,`category`,`subcategory`,`extdata`) VALUES
('Organization', '0', 'CACHE', 'REDIS', '{\"hostname\":\"host\",\"port\":6379,\"password\":\"-\",\"maxTotal\":10,\"maxIdle\":2}'),
('Organization', '0', 'STORAGE', 'QINIU', '{\"key\":\"-\",\"secret\":\"-\",\"bucket\":\"-\",\"domain\":\"http://xxx.bkt.clouddn.com/\"}'),
('Organization', '0', 'MSG', 'EMAIL', '{\"email.sender.name\":\"发送者名称\",\"email.sender.address\":\"service@126.com\",\"email.sender.password\":\"-\",\"email.sender.host\":\"smtp.126.com\",\"email.sender.sslport\":\"\"}'),
('Organization', '0', 'MSG', 'SMS_ETON', '{\"sms.eton.mturl\":\"http://esms100.10690007.net/sms/mt\",\"sms.eton.spid\":9992,\"sms.eton.password\":\"-\",\"sms.eton.sa\":\"-\",\"sms.eton.signcode\":\"01\"}'),
('Organization', '0', 'MSG', 'SMS_SUBMAIL', '{\"sms.submail.appid\":\"-\", \"sms.submail.appkey.secret\":\"-\"}');

-- 定时任务
-- DROP TABLE IF EXISTS `timer_task`;
CREATE TABLE `timer_task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `business_id` bigint(18) DEFAULT NULL COMMENT '业务id',
  `type` varchar(50) DEFAULT NULL COMMENT '任务类型',
  `executor` varchar(100) NOT NULL COMMENT '执行者',
  `schedule_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '定时时间',
  `status` varchar(20) NOT NULL DEFAULT 'NEW' COMMENT '状态',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `progress` decimal(4,1) DEFAULT '0.0' COMMENT '进度',
  `comment` varchar(100) DEFAULT NULL COMMENT '备注',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- 跟踪日志
-- DROP TABLE IF EXISTS `trace_log`;
CREATE TABLE `trace_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `source` varchar(16) NOT NULL COMMENT '来源应用',
  `user_type` varchar(16) NOT NULL DEFAULT 'User' COMMENT '用户类型',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `operation` varchar(32) NOT NULL COMMENT '操作',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求URL',
  `comment` varchar(255) DEFAULT NULL COMMENT '操作备注',
  `rel_obj_type` varchar(32) DEFAULT NULL COMMENT '操作对象',
  `rel_obj_id` varchar(32) DEFAULT NULL COMMENT '操作对象ID',
  `rel_obj_data` varchar(5120) DEFAULT NULL COMMENT '关联数据',
  `extdata` varchar(255) DEFAULT NULL COMMENT '扩展',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_operate_log` (`rel_obj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='跟踪日志';

-- 系统用户表
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `org_id` int(11) NOT NULL DEFAULT '0' COMMENT '单位id',
  `department_id` int(11) DEFAULT '0' COMMENT '部门id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '加密盐',
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `roles` varchar(100) NOT NULL COMMENT '角色',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(20) DEFAULT NULL COMMENT '座机',
  `email` varchar(100) DEFAULT NULL COMMENT 'Email',
  `wechat` varchar(100) DEFAULT NULL COMMENT '微信',
  `openid` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `access_token` varchar(255) DEFAULT NULL COMMENT 'token',
  `fail_count` smallint(3) NOT NULL DEFAULT '0' COMMENT '登录失败次数',
  `expired_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账号状态',
  `extdata` varchar(255) DEFAULT NULL COMMENT 'JSON扩展',
  `active` tinyint(1) DEFAULT '1' COMMENT '有效标记',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_user_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

INSERT INTO `user` VALUES ('10000', '0', '0', 'admin', '3c5bb3515219c3b3e8e135053c7ff4bb', '26bebdc6e218452786dc3ad7dd45fb0b', '管理员', 'ADMIN', null, null, '/static/img/avatar.jpg', '13012345678', null, 'test@163.com', '', null, null, '0', null, '1', null, '1', null, '2018-08-08 18:18:18');

-- 用户角色
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';

INSERT INTO `user_role` VALUES ('10000', 'ADMIN');

-- 单位
-- DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级单位ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `short_name` varchar(50) NOT NULL COMMENT '简称',
  `logo` varchar(255) DEFAULT NULL COMMENT 'Logo',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `telphone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT 'Email',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `website` varchar(255) DEFAULT NULL COMMENT '网址',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4 COMMENT='组织单位';

-- 单位样例数据
INSERT INTO `organization` VALUES ('100000', '0', '苏州帝博信息技术有限公司', '帝博信息', '', '江苏苏州', '0512-12345678', 'service@dibo.ltd', '', 'www.dibo.ltd', 'Diboot', '1', null, '2018-08-08 18:18:18');
