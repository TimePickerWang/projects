/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : tensquare_gathering

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 29/05/2019 21:54:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_gathering
-- ----------------------------
DROP TABLE IF EXISTS `tb_gathering`;
CREATE TABLE `tb_gathering`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '大会简介',
  `detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '详细说明',
  `sponsor` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办方',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动图片',
  `starttime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime(0) NULL DEFAULT NULL COMMENT '截止时间',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '举办地点',
  `enrolltime` datetime(0) NULL DEFAULT NULL COMMENT '报名截止',
  `state` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否可见',
  `city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_gathering
-- ----------------------------
INSERT INTO `tb_gathering` VALUES ('1', '测试活动', '喝茶看电影，不亦乐乎', '喝茶看电影，不亦乐乎', '黑马程序员', NULL, '2017-12-14 15:39:32', '2017-12-21 15:39:36', NULL, NULL, '1', '1');
INSERT INTO `tb_gathering` VALUES ('94377594140', 'aaaa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '1');
INSERT INTO `tb_gathering` VALUES ('943776146707845', 'aaaa', NULL, NULL, 'ssss', NULL, NULL, NULL, 'cccc', NULL, '1', '1');
INSERT INTO `tb_gathering` VALUES ('943776663576121344', 'aaaa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2');
INSERT INTO `tb_gathering` VALUES ('943783521749700608', '2342423', NULL, NULL, '23454534', NULL, NULL, NULL, '545435435', NULL, '1', '2');
INSERT INTO `tb_gathering` VALUES ('944085821768732672', 'JAVAEE茶话会', NULL, NULL, '传智', NULL, NULL, NULL, '金燕龙', NULL, '1', '2');
INSERT INTO `tb_gathering` VALUES ('944086086991351808', '是', NULL, NULL, '11', NULL, NULL, NULL, '11', NULL, '1', '3');
INSERT INTO `tb_gathering` VALUES ('944090372710207488', '大讨论', NULL, NULL, '小马', NULL, NULL, NULL, '消息', NULL, '1', '3');
INSERT INTO `tb_gathering` VALUES ('944105652622594048', '测试测试', NULL, NULL, '测试者', NULL, NULL, NULL, '测试地址', NULL, '1', '4');
INSERT INTO `tb_gathering` VALUES ('945227340642914304', '111', NULL, NULL, '222', NULL, NULL, NULL, '333', NULL, '1', '5');
INSERT INTO `tb_gathering` VALUES ('945227678527655936', '111', NULL, NULL, '222', NULL, NULL, NULL, '333', NULL, '1', '5');
INSERT INTO `tb_gathering` VALUES ('945235087564345344', '啊啊', NULL, NULL, '1', NULL, NULL, NULL, '1', NULL, '1', '1');
INSERT INTO `tb_gathering` VALUES ('945235534329024512', '1', NULL, NULL, '1', NULL, NULL, NULL, '1', NULL, '1', '2');
INSERT INTO `tb_gathering` VALUES ('945235859786043392', '1', NULL, NULL, '1', NULL, NULL, NULL, '1', NULL, '1', '3');
INSERT INTO `tb_gathering` VALUES ('951384896167874560', '??', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_usergath
-- ----------------------------
DROP TABLE IF EXISTS `tb_usergath`;
CREATE TABLE `tb_usergath`  (
  `userid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `gathid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动ID',
  `exetime` datetime(0) NULL DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`userid`, `gathid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户关注活动' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_usergath
-- ----------------------------
INSERT INTO `tb_usergath` VALUES ('1', '200', '2018-01-06 15:44:04');

SET FOREIGN_KEY_CHECKS = 1;
