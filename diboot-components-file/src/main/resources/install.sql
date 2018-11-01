SET FOREIGN_KEY_CHECKS=0;

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

-- 样例参数配置
-- INSERT INTO excel_column(model_class, model_field, col_name, col_index, data_type, validation) VALUES
-- ('ExcelExample', 'realname', '姓名', 1, 'String', 'NotNull'), ('ExcelExample', 'gender', '性别', 2, 'String', 'NotNull,Length(1)'), ('ExcelExample', 'address', '地址', 3, 'String', 'Length(-255)');

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