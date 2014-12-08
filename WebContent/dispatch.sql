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
DROP DATABASE `carshop`;
CREATE SCHEMA `carshop` DEFAULT CHARACTER SET utf8 ;
USE carshop;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TBL_PROPERTY`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_PROPERTY`;
CREATE TABLE `TBL_PROPERTY` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(48) NOT NULL UNIQUE,
  `value` varchar(48) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `TBL_USERGROUP`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_USERGROUP`;
CREATE TABLE `TBL_USERGROUP` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(48) NOT NULL UNIQUE,      /*0: admin; 1:waiter; 2:manager; 3:worker; 4:washer*/
  `comment` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_ORDER
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_USER`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_USER`;
CREATE TABLE `TBL_USER` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(48) NOT NULL UNIQUE,
  `is_admin` smallint(1) NOT NULL,
  `img_path` varchar(48) DEFAULT NULL,
  `passwd` varchar(48) NOT NULL,
  `is_valid` smallint(1) NOT NULL,
  `group_id` bigint(10) NOT NULL,
  `counter` varchar(48) DEFAULT NULL,
  `isBooker` smallint(1) NOT NULL DEFAULT 0,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX group_ind (group_id),
    FOREIGN KEY (group_id)
    REFERENCES TBL_USERGROUP (id)
    ON DELETE no action,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_USER
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_ORDER`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_ORDER`;
CREATE TABLE `TBL_ORDER` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `status` bigint(10) NOT NULL DEFAULT 0,            /*0: subscribe; 1: serve start; */
  `registerNumber` varchar(48) DEFAULT NULL,
  `queueNumber` varchar(48) DEFAULT NULL,   
  `roofNumber` varchar(48) DEFAULT NULL,
  `estimationTime` bigint(10) DEFAULT NULL,
  `bookNumber` varchar(48) DEFAULT NULL,
  `is_book` smallint(1) NOT NULL DEFAULT 0, 
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `start_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `end_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `promiseTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_ORDER
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_SERVEQUEUE`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_SERVEQUEUE`;
CREATE TABLE `TBL_SERVEQUEUE` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `step` bigint(10) NOT NULL,     /*0: start; 1: hold; 2: end*/
  `user_id` bigint(10) NOT NULL,
  `order_id` bigint(10) NOT NULL UNIQUE,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `start_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `end_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  INDEX user_ind (user_id),
    FOREIGN KEY (user_id)
    REFERENCES TBL_USER (id)
    ON DELETE no action,
  INDEX order_ind (order_id),
    FOREIGN KEY (order_id)
    REFERENCES TBL_ORDER (id)
    ON DELETE no action,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_SERVEQUEUE
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_MODIFYQUEUE`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_MODIFYQUEUE`;
CREATE TABLE `TBL_MODIFYQUEUE` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `step` bigint(10) NOT NULL,
  `user_id` bigint(10) NOT NULL,
  `order_id` bigint(10) NOT NULL,
  `jobType` varchar(48) NOT NULL,
  `technician` varchar(48) NOT NULL,
  `isWarrant` smallint(1) NOT NULL,
  `isSubContract` smallint(1) NOT NULL,
  `comment` varchar(512) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `start_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `end_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `hold_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  INDEX user_ind (user_id),
    FOREIGN KEY (user_id)
    REFERENCES TBL_USER (id)
    ON DELETE no action,
   INDEX order_ind (order_id),
    FOREIGN KEY (order_id)
    REFERENCES TBL_ORDER (id)
    ON DELETE no action,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_ORDER
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_CASHQUEUE`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_CASHQUEUE`;
CREATE TABLE `TBL_CASHQUEUE` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `step` bigint(10) NOT NULL,
  `user_id` bigint(10) NOT NULL,
  `order_id` bigint(10) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `start_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  `end_time` timestamp  NOT NULL DEFAULT '1990-01-01 00:00:00',
  INDEX user_ind (user_id),
    FOREIGN KEY (user_id)
    REFERENCES TBL_USER (id)
    ON DELETE no action,
  INDEX order_ind (order_id),
    FOREIGN KEY (order_id)
    REFERENCES TBL_ORDER (id)
    ON DELETE no action,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_ORDER
-- ----------------------------

-- ----------------------------
-- Table structure for `TBL_USERWORKLOAD`
-- ----------------------------
DROP TABLE IF EXISTS `TBL_USERWORKLOAD`;
CREATE TABLE `TBL_USERWORKLOAD` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) NOT NULL,
  `modifyqueue_id` bigint(10) NOT NULL,
  `humanResource` varchar(48) NOT NULL,
  `generalRepaire` varchar(48) NOT NULL,
  `additionalHours` varchar(48) NOT NULL,
  `comment` varchar(48) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `allocated_time` timestamp NOT NULL DEFAULT '1990-01-01 00:00:00',
  INDEX user_ind (user_id),
    FOREIGN KEY (user_id)
    REFERENCES TBL_USER (id)
    ON DELETE no action,
  INDEX modifyqueue_ind (modifyqueue_id),
    FOREIGN KEY (modifyqueue_id)
    REFERENCES TBL_MODIFYQUEUE (id)
    ON DELETE no action,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of TBL_USERWORKLOAD
-- ----------------------------


-- ----------------------------
-- Records of d_log
-- ----------------------------
-- INSERT INTO `d_log` VALUES ('62', 'e2b22b89df5a423eb5da984bff1c0d61', 'uuid：e2b22b89df5a423eb5da984bff1c0d61满足依赖关系,插入active表中', '2014-07-11 02:22:02', '2014-07-11 02:22:02', 'r_new_user_list_hs.sql 20140710', 'hivewrapper', null, '2014-07-10');

insert into tbl_usergroup set group_name = 0;
insert into tbl_usergroup set group_name = 1;
insert into tbl_usergroup set group_name = 2;
insert into tbl_user set user_name = 'admin', is_admin = 1, passwd = 'passw0rd', is_valid = 1, group_id = 3;
insert into tbl_user set user_name = 'booker-1', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 4;
insert into tbl_user set user_name = 'booker-2', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 4;
insert into tbl_user set user_name = 'SA-01', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 5, counter='COUNTER 1';
insert into tbl_user set user_name = 'SA-02', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 5, counter='COUNTER 2';
insert into tbl_user set user_name = 'SA-03', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 5, counter='COUNTER 3';
insert into tbl_user set user_name = 'SA-04', is_admin = 0, passwd = 'passw0rd', is_valid = 1, group_id = 5, counter='COUNTER 4';

insert into tbl_order set status=0, registerNumber='B-001', queueNumber='B1831TOK', estimationTime=4, start_time='2014-12-02 12:02:06', end_time='2014-12-02 12:12:26';
insert into tbl_order set status=0, registerNumber='N-001', queueNumber='B1093MN', estimationTime=5;
insert into tbl_order set status=0, registerNumber='B-002', queueNumber='B8763HJ', estimationTime=6;
insert into tbl_order set status=1, registerNumber='B-003', queueNumber='B1001AC', estimationTime=7;
insert into tbl_order set status=1, registerNumber='B-002', queueNumber='B1002AC', estimationTime=8;
insert into tbl_order set status=1, registerNumber='B-001', queueNumber='B1003AC', estimationTime=4;
insert into tbl_order set status=1, registerNumber='N-001', queueNumber='B1001NC', estimationTime=5;

insert into tbl_servequeue set step=0, user_id=6, order_id=3;
insert into tbl_servequeue set step=0, user_id=7, order_id=4;
insert into tbl_servequeue set step=0, user_id=8, order_id=5;
insert into tbl_servequeue set step=0, user_id=9, order_id=6;

insert into tbl_property set name='COUNTER_NUM', value='5';
