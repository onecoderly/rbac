/*
 Navicat Premium Data Transfer

 Source Server         : Blue
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : rbac

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 18/03/2020 20:11:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for power
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父权限ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限URL',
  `type` int(1) UNSIGNED NULL DEFAULT 0 COMMENT '权限类型：0资源  1菜单',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES (3, 12, '用户管理', '/user/**', 0, 1);
INSERT INTO `power` VALUES (4, 12, '角色管理', '/role/**', 0, 1);
INSERT INTO `power` VALUES (5, 12, '权限管理', '/power/**', 0, 1);
INSERT INTO `power` VALUES (12, NULL, '首页', '/', 0, 1);
INSERT INTO `power` VALUES (25, NULL, '权限管理', NULL, 1, 1);
INSERT INTO `power` VALUES (26, 25, '用户管理', '/user', 1, 1);
INSERT INTO `power` VALUES (27, 25, '角色管理', '/role', 1, 1);
INSERT INTO `power` VALUES (28, 25, '权限管理', '/power', 1, 1);
INSERT INTO `power` VALUES (30, NULL, '首页', NULL, 1, 1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `status` int(255) UNSIGNED NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (2, '管理员', 1);
INSERT INTO `role` VALUES (5, '超级管理员', 1);
INSERT INTO `role` VALUES (6, '会员管理员', 1);

-- ----------------------------
-- Table structure for role_power
-- ----------------------------
DROP TABLE IF EXISTS `role_power`;
CREATE TABLE `role_power`  (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `power_id` int(11) NOT NULL COMMENT '权限ID'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_power
-- ----------------------------
INSERT INTO `role_power` VALUES (5, 12);
INSERT INTO `role_power` VALUES (5, 3);
INSERT INTO `role_power` VALUES (5, 4);
INSERT INTO `role_power` VALUES (5, 5);
INSERT INTO `role_power` VALUES (5, 25);
INSERT INTO `role_power` VALUES (5, 26);
INSERT INTO `role_power` VALUES (5, 27);
INSERT INTO `role_power` VALUES (5, 28);
INSERT INTO `role_power` VALUES (5, 30);
INSERT INTO `role_power` VALUES (6, 12);
INSERT INTO `role_power` VALUES (6, 3);
INSERT INTO `role_power` VALUES (6, 25);
INSERT INTO `role_power` VALUES (6, 26);
INSERT INTO `role_power` VALUES (6, 30);
INSERT INTO `role_power` VALUES (2, 12);
INSERT INTO `role_power` VALUES (2, 3);
INSERT INTO `role_power` VALUES (2, 4);
INSERT INTO `role_power` VALUES (2, 25);
INSERT INTO `role_power` VALUES (2, 26);
INSERT INTO `role_power` VALUES (2, 27);
INSERT INTO `role_power` VALUES (2, 30);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  `last_user_id` int(11) NULL DEFAULT NULL COMMENT '最后修改人',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态',
  `head_portrait` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'Blue', '9386bb1310347c936dfd385a55af1dc5', '2019-11-19 10:35:53', '2020-03-18 19:23:59', NULL, 1, '2a29a85ebb04u=1316375559,2127531467&fm=26&gp=0.jpg');
INSERT INTO `user_info` VALUES (2, '哈哈', '4c2197519c4178657ce6916daa2b096b', '2019-11-19 11:02:49', '2020-03-18 20:10:33', NULL, 0, NULL);
INSERT INTO `user_info` VALUES (3, '嘻嘻', '4e15ab963928364c89219701dd68d227', '2019-11-19 11:03:09', NULL, NULL, 1, NULL);
INSERT INTO `user_info` VALUES (10, 'user', '710cde70788f332f8937521a9eaa4c69', '2019-11-21 11:22:13', '2019-11-21 17:03:09', NULL, 0, NULL);
INSERT INTO `user_info` VALUES (11, 'user', '24c9e15e52afc47c225b757e7bee1f9d', '2019-11-21 17:03:17', NULL, NULL, 1, NULL);
INSERT INTO `user_info` VALUES (12, 'admin', 'e00cf25ad42683b3df678c61f42c6bda', '2019-11-21 17:04:15', NULL, NULL, 1, NULL);
INSERT INTO `user_info` VALUES (13, 'xixi', 'bd63e3e1e52833ca0450d44a0890dc46', '2020-01-06 22:51:59', '2020-03-18 19:27:52', NULL, 1, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 5);
INSERT INTO `user_role` VALUES (10, 6);
INSERT INTO `user_role` VALUES (11, 6);
INSERT INTO `user_role` VALUES (12, 2);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (13, 2);

SET FOREIGN_KEY_CHECKS = 1;
