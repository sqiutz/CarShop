/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.85_3306
Source Server Version : 50519
Source Host           : 192.168.1.85:3306
Source Database       : dispatch

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2014-07-11 15:28:07
*/

CREATE SCHEMA `carshop` DEFAULT CHARACTER SET utf8 ;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `d_active`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_USER`;
CREATE TABLE `TBL_USER` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) NOT NULL,
  `user_name` varchar(48) NOT NULL,
  `user_type` bigint(10) NOT NULL,
  `is_admin` smallint(1) NOT NULL,
  `img_path` varchar(48) DEFAULT NULL,
  `passwd` varchar(48) NOT NULL,
  `is_valid` smallint(1) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_active
-- ----------------------------

-- ----------------------------
-- Table structure for `d_active`
-- ----------------------------
DROP TABLE IF EXISTS `d_active`;
CREATE TABLE `d_active` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(48) NOT NULL,
  `startingdate` datetime DEFAULT NULL,
  `priority` smallint(1) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_active
-- ----------------------------

-- ----------------------------
-- Table structure for `d_log`
-- ----------------------------
DROP TABLE IF EXISTS `d_log`;
CREATE TABLE `d_log` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(48) NOT NULL,
  `log` text,
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `params` varchar(128) DEFAULT NULL,
  `shell_order` varchar(48) NOT NULL,
  `owner` varchar(2048) DEFAULT NULL,
  `biz_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_log
-- ----------------------------
INSERT INTO `d_log` VALUES ('62', 'e2b22b89df5a423eb5da984bff1c0d61', 'uuid：e2b22b89df5a423eb5da984bff1c0d61满足依赖关系,插入active表中', '2014-07-11 02:22:02', '2014-07-11 02:22:02', 'r_new_user_list_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('63', 'c5dee80673764c5c93cd36140029ad6d', 'uuid：c5dee80673764c5c93cd36140029ad6d满足依赖关系,插入active表中', '2014-07-11 02:22:02', '2014-07-11 02:22:02', 'r_start_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('64', 'e2b22b89df5a423eb5da984bff1c0d61', '启动等待任务 uuid:e2b22b89df5a423eb5da984bff1c0d61', '2014-07-11 04:05:10', '2014-07-11 04:05:10', 'r_new_user_list_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('65', 'e2b22b89df5a423eb5da984bff1c0d61', '调用成功', '2014-07-11 04:06:27', '2014-07-11 04:06:27', 'r_new_user_list_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('66', 'b6addfd449a7499994713dc98121da2b', 'uuid：b6addfd449a7499994713dc98121da2b满足依赖关系,插入active表中', '2014-07-11 04:06:27', '2014-07-11 04:06:27', 'r_new_user_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('67', 'c5dee80673764c5c93cd36140029ad6d', '启动等待任务 uuid:c5dee80673764c5c93cd36140029ad6d', '2014-07-11 05:10:10', '2014-07-11 05:10:10', 'r_start_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('68', 'b6addfd449a7499994713dc98121da2b', '启动等待任务 uuid:b6addfd449a7499994713dc98121da2b', '2014-07-11 05:10:10', '2014-07-11 05:10:10', 'r_new_user_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('69', 'c5dee80673764c5c93cd36140029ad6d', '调用成功', '2014-07-11 05:10:47', '2014-07-11 05:10:47', 'r_start_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('70', 'b93ce2e4887e40ad921e09cab42c5871', 'uuid：b93ce2e4887e40ad921e09cab42c5871满足依赖关系,插入active表中', '2014-07-11 05:10:47', '2014-07-11 05:10:47', 'r_start_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('71', 'b6addfd449a7499994713dc98121da2b', '调用成功', '2014-07-11 05:10:50', '2014-07-11 05:10:50', 'r_new_user_cnt_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('72', 'b93ce2e4887e40ad921e09cab42c5871', '启动等待任务 uuid:b93ce2e4887e40ad921e09cab42c5871', '2014-07-11 05:10:50', '2014-07-11 05:10:50', 'r_start_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('73', '787320efaf594b27b4e093a30f9e6dbc', 'uuid：787320efaf594b27b4e093a30f9e6dbc满足依赖关系,插入active表中', '2014-07-11 05:10:50', '2014-07-11 05:10:50', 'r_new_user_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('74', '787320efaf594b27b4e093a30f9e6dbc', '启动等待任务 uuid:787320efaf594b27b4e093a30f9e6dbc', '2014-07-11 05:11:00', '2014-07-11 05:11:00', 'r_new_user_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('75', 'b93ce2e4887e40ad921e09cab42c5871', '调用成功', '2014-07-11 05:11:30', '2014-07-11 05:11:30', 'r_start_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('76', '787320efaf594b27b4e093a30f9e6dbc', '调用成功', '2014-07-11 05:11:31', '2014-07-11 05:11:31', 'r_new_user_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('77', '8980b361b88545ca89168f42fe9c4dd9', 'uuid：8980b361b88545ca89168f42fe9c4dd9满足依赖关系,插入active表中', '2014-07-11 14:58:00', '2014-07-11 14:58:00', '20140710', 'a.sh', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('78', '8980b361b88545ca89168f42fe9c4dd9', '启动等待任务 uuid:8980b361b88545ca89168f42fe9c4dd9', '2014-07-11 14:58:10', '2014-07-11 14:58:10', '20140710', 'a.sh', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('79', '8980b361b88545ca89168f42fe9c4dd9', '调用成功', '2014-07-11 14:58:10', '2014-07-11 14:58:10', '20140710', 'a.sh', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('80', '2942310059e249399907c587013a898b', 'uuid：2942310059e249399907c587013a898b满足依赖关系,插入active表中', '2014-07-11 15:06:00', '2014-07-11 15:06:00', '20140710', 'a.sh', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('81', '2942310059e249399907c587013a898b', '启动等待任务 uuid:2942310059e249399907c587013a898b', '2014-07-11 15:06:10', '2014-07-11 15:06:10', '20140710', 'a.sh', null, '2014-07-10');
INSERT INTO `d_log` VALUES ('82', '2942310059e249399907c587013a898b', 'run task faile :expr: division by zero', '2014-07-11 15:06:10', '2014-07-11 15:06:10', '20140710', 'a.sh', null, '2014-07-10');

-- ----------------------------
-- Table structure for `m_dependency`
-- ----------------------------
DROP TABLE IF EXISTS `m_dependency`;
CREATE TABLE `m_dependency` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(48) NOT NULL,
  `parent_uuid` varchar(48) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `uuid` (`uuid`,`parent_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_dependency
-- ----------------------------
INSERT INTO `m_dependency` VALUES ('227', 'b6addfd449a7499994713dc98121da2b', 'e2b22b89df5a423eb5da984bff1c0d61', '2014-07-11 02:22:02', '2014-07-11 02:22:02');
INSERT INTO `m_dependency` VALUES ('228', 'e2b22b89df5a423eb5da984bff1c0d61', '0', '2014-07-11 02:22:02', '2014-07-11 02:22:02');
INSERT INTO `m_dependency` VALUES ('229', 'c5dee80673764c5c93cd36140029ad6d', '0', '2014-07-11 02:22:02', '2014-07-11 02:22:02');
INSERT INTO `m_dependency` VALUES ('230', '787320efaf594b27b4e093a30f9e6dbc', 'b6addfd449a7499994713dc98121da2b', '2014-07-11 02:22:02', '2014-07-11 02:22:02');
INSERT INTO `m_dependency` VALUES ('231', 'b93ce2e4887e40ad921e09cab42c5871', 'c5dee80673764c5c93cd36140029ad6d', '2014-07-11 02:22:02', '2014-07-11 02:22:02');
INSERT INTO `m_dependency` VALUES ('232', '8980b361b88545ca89168f42fe9c4dd9', '0', '2014-07-11 14:58:00', '2014-07-11 14:58:00');
INSERT INTO `m_dependency` VALUES ('233', '2942310059e249399907c587013a898b', '0', '2014-07-11 15:06:00', '2014-07-11 15:06:00');

-- ----------------------------
-- Table structure for `m_shellorder`
-- ----------------------------
DROP TABLE IF EXISTS `m_shellorder`;
CREATE TABLE `m_shellorder` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(48) NOT NULL,
  `taskId` varchar(48) NOT NULL,
  `startingdate` datetime NOT NULL,
  `params` varchar(128) DEFAULT '' COMMENT '{1:A,2:B,3:c}',
  `shell_order` varchar(48) NOT NULL,
  `owner` varchar(2048) DEFAULT NULL,
  `status` smallint(1) NOT NULL COMMENT '-1 failed; 0 success;1 waiting;2running;3satisfydependency',
  `source` smallint(1) NOT NULL COMMENT '1 来自源表 2 来自外部',
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `biz_date` date NOT NULL,
  `max_runtime` bigint(20) DEFAULT NULL COMMENT '序程运行的最大时间',
  `priority` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid,taskId` (`uuid`,`taskId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of m_shellorder
-- ----------------------------
INSERT INTO `m_shellorder` VALUES ('496', 'e2b22b89df5a423eb5da984bff1c0d61', '1', '2014-07-11 04:05:01', 'r_new_user_list_hs.sql 20140710', 'hivewrapper', null, '0', '1', '2014-07-11 02:22:02', '2014-07-11 04:06:27', '2014-07-11 04:05:10', '2014-07-11 04:06:27', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('497', 'b6addfd449a7499994713dc98121da2b', '21', '2014-07-11 05:10:02', 'r_new_user_cnt_hs.sql 20140710', 'hivewrapper', null, '0', '1', '2014-07-11 02:22:02', '2014-07-11 05:10:50', '2014-07-11 05:10:10', '2014-07-11 05:10:50', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('498', '787320efaf594b27b4e093a30f9e6dbc', '31', '2014-07-11 05:10:02', 'r_new_user_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '0', '1', '2014-07-11 02:22:02', '2014-07-11 05:11:31', '2014-07-11 05:11:00', '2014-07-11 05:11:31', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('499', 'c5dee80673764c5c93cd36140029ad6d', '41', '2014-07-11 05:10:02', 'r_start_cnt_hs.sql 20140710', 'hivewrapper', null, '0', '1', '2014-07-11 02:22:02', '2014-07-11 05:10:47', '2014-07-11 05:10:10', '2014-07-11 05:10:47', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('500', 'b93ce2e4887e40ad921e09cab42c5871', '51', '2014-07-11 05:10:02', 'r_start_cnt_hs appid.time.hour 1,0 20140710', 'hive2hbase', null, '0', '1', '2014-07-11 02:22:02', '2014-07-11 05:11:30', '2014-07-11 05:10:50', '2014-07-11 05:11:30', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('501', '8980b361b88545ca89168f42fe9c4dd9', '61', '2014-07-11 05:10:00', '20140710', 'a.sh', null, '0', '1', '2014-07-11 14:58:00', '2014-07-11 14:58:10', '2014-07-11 14:58:10', '2014-07-11 14:58:10', '2014-07-10', null, '1');
INSERT INTO `m_shellorder` VALUES ('502', '2942310059e249399907c587013a898b', '61', '2014-07-11 05:10:00', '20140710', 'a.sh', null, '-1', '1', '2014-07-11 15:06:00', '2014-07-11 15:06:10', '2014-07-11 15:06:10', '2014-07-11 15:06:10', '2014-07-10', null, '1');

-- ----------------------------
-- Table structure for `runtasknum`
-- ----------------------------
DROP TABLE IF EXISTS `runtasknum`;
CREATE TABLE `runtasknum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `runTaskTotleNum` bigint(4) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of runtasknum
-- ----------------------------
INSERT INTO `runtasknum` VALUES ('1', '0', '2014-06-26 12:04:50', '2014-07-11 15:07:10');

-- ----------------------------
-- Table structure for `s_category`
-- ----------------------------
DROP TABLE IF EXISTS `s_category`;
CREATE TABLE `s_category` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `categoryId` varchar(48) NOT NULL,
  `order_prefix` varchar(48) DEFAULT NULL COMMENT '{1:a;2:b}',
  `location` varchar(48) DEFAULT NULL COMMENT '命令所在的路径',
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `categoryId` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_category
-- ----------------------------
INSERT INTO `s_category` VALUES ('1', '2', '', '', '2014-06-13 14:04:21', '2014-07-08 12:23:51');
INSERT INTO `s_category` VALUES ('2', '21', '', '', '2014-06-13 14:04:45', '2014-07-08 12:23:49');
INSERT INTO `s_category` VALUES ('4', '31', 'sh', '/home/hadoop/tomcat/test', '1990-01-01 00:00:00', '2014-07-11 14:43:48');

-- ----------------------------
-- Table structure for `s_dependency`
-- ----------------------------
DROP TABLE IF EXISTS `s_dependency`;
CREATE TABLE `s_dependency` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `taskId` varchar(48) NOT NULL,
  `parent_taskId` varchar(48) NOT NULL COMMENT '用逗号分隔',
  `flag` smallint(1) NOT NULL COMMENT '否是删除',
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `taskId` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_dependency
-- ----------------------------
INSERT INTO `s_dependency` VALUES ('1', '1', '0', '1', '2014-06-13 14:06:53', '2014-06-18 14:54:58');
INSERT INTO `s_dependency` VALUES ('2', '21', '1', '1', '2014-06-13 14:07:00', '2014-07-08 14:36:13');
INSERT INTO `s_dependency` VALUES ('3', '31', '21', '1', '2014-07-08 15:04:46', '2014-07-08 15:04:47');
INSERT INTO `s_dependency` VALUES ('4', '41', '0', '1', '2014-07-09 14:09:19', '2014-07-09 14:09:18');
INSERT INTO `s_dependency` VALUES ('5', '51', '41', '1', '2014-07-09 14:09:52', '2014-07-09 14:09:48');
INSERT INTO `s_dependency` VALUES ('7', '61', '0', '1', '1990-01-01 00:00:00', '2014-07-11 14:44:07');

-- ----------------------------
-- Table structure for `s_shellorder`
-- ----------------------------
DROP TABLE IF EXISTS `s_shellorder`;
CREATE TABLE `s_shellorder` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `taskId` varchar(48) NOT NULL,
  `categoryId` varchar(48) NOT NULL,
  `shell_order` varchar(48) NOT NULL,
  `cron` varchar(128) NOT NULL,
  `params` varchar(128) DEFAULT '' COMMENT '用空格分隔输入参数,不要有引号（操作系统会处理引号，产生问题）',
  `flag` smallint(1) NOT NULL COMMENT '否是删除 -1 删除 1 exist',
  `owner` varchar(2048) DEFAULT NULL,
  `comment` varchar(2048) DEFAULT NULL,
  `createtime` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `max_runtime` bigint(20) DEFAULT NULL COMMENT '序程运行的最大时间',
  `taskweight` smallint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `taskId` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_shellorder
-- ----------------------------
INSERT INTO `s_shellorder` VALUES ('1', '1', '2', 'hivewrapper', '5 4 * * *', 'r_new_user_list_hs.sql ${bizDate}', '-1', null, null, '2014-06-12 18:19:14', '2014-07-11 14:43:19', null, '1');
INSERT INTO `s_shellorder` VALUES ('3', '21', '21', 'hivewrapper', '10 5 * * *', 'r_new_user_cnt_hs.sql ${bizDate}', '-1', null, null, '2014-06-18 17:57:08', '2014-07-11 14:43:16', null, '1');
INSERT INTO `s_shellorder` VALUES ('4', '31', '2', 'hive2hbase', '10 5 * * *', 'r_new_user_cnt_hs appid.time.hour 1,0 ${bizDate}', '-1', null, null, '2014-07-08 14:56:39', '2014-07-11 14:43:14', null, '1');
INSERT INTO `s_shellorder` VALUES ('5', '41', '2', 'hivewrapper', '10 5 * * *', 'r_start_cnt_hs.sql ${bizDate}', '-1', null, null, '2014-07-09 14:28:30', '2014-07-11 14:43:13', null, '1');
INSERT INTO `s_shellorder` VALUES ('6', '51', '21', 'hive2hbase', '10 5 * * *', 'r_start_cnt_hs appid.time.hour 1,0 ${bizDate}', '-1', null, null, '2014-07-09 14:28:34', '2014-07-11 14:43:11', null, '1');
INSERT INTO `s_shellorder` VALUES ('8', '61', '31', 'a.sh', '10 5 * * *', '${bizDate}', '1', null, null, '1990-01-01 00:00:00', '2014-07-11 14:43:09', null, '1');
