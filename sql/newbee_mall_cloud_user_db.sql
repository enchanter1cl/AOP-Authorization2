/*
 Navicat Premium Data Transfer

 Source Server         : 121.37.71.228
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : 121.37.71.228:3308
 Source Schema         : newbee_mall_cloud_user_db

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 06/06/2023 12:06:19
*/
CREATE DATABASE `newbee_mall_cloud_user_db`;
USE `newbee_mall_cloud_user_db`

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_newbee_mall_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_newbee_mall_admin_user`;
CREATE TABLE `tb_newbee_mall_admin_user`  (
  `admin_user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint(0) NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_newbee_mall_admin_user
-- ----------------------------
INSERT INTO `tb_newbee_mall_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '十三', 0);
INSERT INTO `tb_newbee_mall_admin_user` VALUES (2, 'newbee-admin1', 'e10adc3949ba59abbe56e057f20f883e', '新蜂01', 0);
INSERT INTO `tb_newbee_mall_admin_user` VALUES (3, 'newbee-admin2', 'e10adc3949ba59abbe56e057f20f883e', '新蜂02', 0);

-- ----------------------------
-- Table structure for tb_newbee_mall_admin_user_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_newbee_mall_admin_user_token`;
CREATE TABLE `tb_newbee_mall_admin_user_token`  (
  `admin_user_id` bigint(0) NOT NULL COMMENT '用户主键id',
  `token` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'token值(32位字符串)',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `expire_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'token过期时间',
  PRIMARY KEY (`admin_user_id`) USING BTREE,
  UNIQUE INDEX `uq_token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_newbee_mall_admin_user_token
-- ----------------------------
INSERT INTO `tb_newbee_mall_admin_user_token` VALUES (1, 'tokennnnn', '2023-06-05 21:19:08', '2023-06-07 00:00:00');
INSERT INTO `tb_newbee_mall_admin_user_token` VALUES (2, 'eae09a7924c5e6a2b68e03695f4d9c71', '2023-06-06 11:58:03', '2023-06-08 11:58:03');

-- ----------------------------
-- Table structure for tb_newbee_mall_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_newbee_mall_user`;
CREATE TABLE `tb_newbee_mall_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '注销标识字段(0-正常 1-已注销)',
  `locked_flag` tinyint(0) NOT NULL DEFAULT 0 COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_newbee_mall_user
-- ----------------------------
INSERT INTO `tb_newbee_mall_user` VALUES (1, '十三', '13700002703', 'e10adc3949ba59abbe56e057f20f883e', '我不怕千万人阻挡，只怕自己投降', 0, 0, '2022-05-22 08:44:57');
INSERT INTO `tb_newbee_mall_user` VALUES (6, '陈尼克', '13711113333', 'e10adc3949ba59abbe56e057f20f883e', '测试用户陈尼克', 0, 0, '2022-05-22 08:44:57');

SET FOREIGN_KEY_CHECKS = 1;
