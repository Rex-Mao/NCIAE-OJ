/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL-Server
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : oj_judgecenter

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 11/03/2020 21:53:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for checkpoint
-- ----------------------------
DROP TABLE IF EXISTS `checkpoint`;
CREATE TABLE `checkpoint` (
  `cpid` int(11) NOT NULL AUTO_INCREMENT COMMENT '检查点ID',
  `pid` bigint(20) NOT NULL COMMENT '题目ID与内容中心同步',
  `input` varchar(2560) COLLATE utf8_bin DEFAULT NULL COMMENT '标准输入',
  `output` varchar(2560) COLLATE utf8_bin DEFAULT NULL COMMENT '标准输出',
  PRIMARY KEY (`cpid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for rocketmq_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `rocketmq_transaction_log`;
CREATE TABLE `rocketmq_transaction_log` (
  `t_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `transaction_id` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '事务ID',
  `t_log` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '事务日志',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='RocketMQ事务表';

SET FOREIGN_KEY_CHECKS = 1;
