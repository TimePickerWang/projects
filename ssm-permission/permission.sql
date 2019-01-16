/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : permission

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(11) NOT NULL DEFAULT 0 COMMENT '权限所在的权限模块id',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '请求的url，可以填正则表达式',
  `type` int(11) NOT NULL DEFAULT 3 COMMENT '类型，1：菜单，2：按钮，3：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES (1, '20190111165044_45', '进入产品管理界面', 1, '/sys/product/product.page', 1, 1, 1, '', 'Admin', '2019-01-16 11:23:53', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (2, '20190111165801_65', '查询产品列表', 1, '/sys/product/page.json', 2, 1, 2, '', 'Admin', '2019-01-11 16:59:15', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (3, '20190111165909_37', '产品上架', 1, '/sys/product/online.json', 2, 1, 3, '', 'Admin', '2019-01-11 16:59:10', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (4, '20190111170006_92', '产品下架', 1, '/sys/product/offline.json', 2, 1, 4, '', 'Admin', '2019-01-11 17:00:06', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (5, '20190112205305_36', '进入订单页', 2, '/sys/order/order.page', 1, 1, 1, '', 'Admin', '2019-01-12 20:53:05', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (6, '20190112205423_83', '查询订单列表', 2, '/sys/order/list.json', 2, 1, 2, '', 'Admin', '2019-01-12 20:54:24', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (7, '20190112205746_15', '进入权限管理页', 7, '/sys/aclModule/acl.page', 1, 1, 1, '', 'Admin', '2019-01-14 20:57:56', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (8, '20190112205850_57', '进入角色管理页', 8, '/sys/role/role.page', 1, 1, 1, '', 'Admin', '2019-01-12 20:58:50', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (9, '20190112205951_61', '进入用户管理页', 9, '/sys/dept/dept.page', 1, 1, 1, '', 'Admin', '2019-01-12 20:59:52', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (10, '20190116112218_27', '进入权限更新记录界面', 12, '/sys/log/log.page', 1, 1, 1, '', 'Admin', '2019-01-16 11:24:01', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES (11, '20190116212031_55', '人力管理页面', 13, '/sys/test', 1, 1, 1, '', 'Admin', '2019-01-16 21:20:50', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限模块名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级权限模块id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
INSERT INTO `sys_acl_module` VALUES (1, '产品管理', 0, '0', 1, 1, 'product', 'Admin', '2019-01-10 17:16:28', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (2, '订单管理', 0, '0', 2, 1, '', 'Admin', '2019-01-10 17:16:30', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (3, '公告管理', 0, '0', 3, 1, '', 'Admin', '2019-01-10 17:34:56', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (4, '出售中产品管理', 1, '0.1', 1, 1, '', 'Admin', '2019-01-10 19:02:18', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (5, '下架产品管理', 1, '0.1', 2, 1, '', 'Admin', '2019-01-10 19:04:25', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (6, '权限管理', 0, '0', 4, 1, '', 'Admin', '2019-01-12 20:55:09', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (7, '权限管理', 6, '0.6', 1, 1, '', 'Admin', '2019-01-12 20:55:42', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (8, '角色管理', 6, '0.6', 2, 1, '', 'Admin', '2019-01-12 20:56:21', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (9, '用户管理', 6, '0.6', 3, 1, '', 'Admin', '2019-01-12 20:56:33', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (11, '运维管理', 0, '0', 6, 1, '运维', 'Admin', '2019-01-16 11:18:39', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (12, '权限更新记录管理', 6, '0.6', 4, 1, '', 'Admin', '2019-01-16 11:19:47', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES (13, '人力管理', 0, '0', 6, 1, '', 'Admin', '2019-01-16 21:19:46', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '上级部门id',
  `level` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '部门在当前层级下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '技术部', 0, '0', 1, '技术部', 'system', '2019-01-04 17:46:45', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (2, '后端开发', 1, '0.1', 1, '后端', 'system', '2019-01-09 14:39:54', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (3, '前端开发', 1, '0.1', 2, '技术部', 'system', '2019-01-04 17:51:33', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (4, 'UI设计', 1, '0.1', 3, '', 'system', '2019-01-07 15:30:24', '127.0.0.1');
INSERT INTO `sys_dept` VALUES (8, '产品部', 0, '0', 2, '', 'Admin', '2019-01-16 11:13:03', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_dept` VALUES (9, '客服部', 0, '0', 4, '', 'Admin', '2019-01-16 11:14:01', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_dept` VALUES (10, '运营部', 0, '0', 4, '', 'Admin', '2019-01-16 21:18:41', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系',
  `target_id` int(11) NOT NULL COMMENT '基于type后指定的对象id，比如用户、权限、角色等表的主键',
  `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '旧值',
  `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '新值',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次操作时间',
  `operate_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次操作者的ip地址',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '当前是否复原过，0：没有，1：复原过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 1, 8, '', '{\"id\":8,\"name\":\"产品部\",\"parentId\":0,\"level\":\"0\",\"seq\":2,\"operator\":\"Admin\",\"operateTime\":1547608383186,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:13:03', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (2, 1, 9, '', '{\"id\":9,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":3,\"operator\":\"Admin\",\"operateTime\":1547608395622,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:13:16', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (3, 1, 9, '{\"id\":9,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":3,\"operator\":\"Admin\",\"operateTime\":1547608396000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":9,\"name\":\"客服部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547608440830,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:14:01', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (4, 2, 4, '', '{\"id\":4,\"username\":\"dote\",\"telephone\":\"13288886666\",\"mail\":\"dote@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547608566446,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:16:07', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (5, 2, 4, '{\"id\":4,\"username\":\"dote\",\"telephone\":\"13288886666\",\"mail\":\"dote@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547608566000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":4,\"username\":\"dote\",\"telephone\":\"13288886666\",\"mail\":\"dote@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"sss\",\"operator\":\"Admin\",\"operateTime\":1547608582990,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:16:23', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (6, 3, 11, '', '{\"id\":11,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547608708593,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:18:29', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (7, 3, 11, '{\"id\":11,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547608709000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":11,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1547608719402,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:18:39', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (8, 3, 12, '', '{\"id\":12,\"name\":\"权限更新记录管理\",\"parentId\":6,\"level\":\"0.6\",\"seq\":4,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547608787235,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:19:47', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (9, 4, 10, '', '{\"id\":10,\"code\":\"20190116112218_27\",\"name\":\"进入权限更新记录界面\",\"aclModuleId\":1,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547608938643,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:22:19', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (10, 4, 1, '{\"id\":1,\"code\":\"20190111165044_45\",\"name\":\"进入产品管理界面\",\"aclModuleId\":1,\"url\":\"/sys/product/product.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547197022000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":1,\"name\":\"进入产品管理界面\",\"aclModuleId\":12,\"url\":\"/sys/product/product.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547608968182,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:22:48', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (11, 4, 1, '{\"id\":1,\"code\":\"20190111165044_45\",\"name\":\"进入产品管理界面\",\"aclModuleId\":12,\"url\":\"/sys/product/product.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547608968000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":1,\"name\":\"进入产品管理界面\",\"aclModuleId\":1,\"url\":\"/sys/product/product.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547609033114,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:23:53', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (12, 4, 10, '{\"id\":10,\"code\":\"20190116112218_27\",\"name\":\"进入权限更新记录界面\",\"aclModuleId\":1,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547608939000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":10,\"name\":\"进入权限更新记录界面\",\"aclModuleId\":12,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547609040648,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:24:01', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (13, 5, 5, '', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547609078937,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:24:39', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (14, 5, 5, '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547609079000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1547609090120,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 11:24:50', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (15, 2, 5, '', '{\"id\":5,\"username\":\"客服小张\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.co,\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644620906,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:17:02', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (16, 2, 5, '{\"id\":5,\"username\":\"客服小张\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.co,\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644621000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"客服小张a\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.com\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644639144,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:17:19', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (17, 2, 5, '{\"id\":5,\"username\":\"客服小张a\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644639000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"客服小张\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.co,\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644646672,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:17:27', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (18, 2, 5, '{\"id\":5,\"username\":\"客服小张\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.co,\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644647000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"客服小张\",\"telephone\":\"13888889999\",\"mail\":\"zhang@qq.com\",\"deptId\":9,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644658222,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:17:38', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (19, 1, 10, '', '{\"id\":10,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547644708639,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:18:29', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (20, 1, 10, '{\"id\":10,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547644709000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":10,\"name\":\"运营部a\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547644716425,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:18:37', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (21, 1, 10, '{\"id\":10,\"name\":\"运营部a\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547644716000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":10,\"name\":\"运营部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1547644720524,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:18:41', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (22, 3, 13, '', '{\"id\":13,\"name\":\"人力管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644774547,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:19:35', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (23, 3, 13, '{\"id\":13,\"name\":\"人力管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644775000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":13,\"name\":\"人力管理a\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644781023,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:19:41', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (24, 3, 13, '{\"id\":13,\"name\":\"人力管理a\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644781000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":13,\"name\":\"人力管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1547644786173,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:19:46', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (25, 4, 11, '', '{\"id\":11,\"code\":\"20190116212031_55\",\"name\":\"人力管理页面\",\"aclModuleId\":13,\"url\":\"/sys/test\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547644831228,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:20:31', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (26, 4, 11, '{\"id\":11,\"code\":\"20190116212031_55\",\"name\":\"人力管理页面\",\"aclModuleId\":13,\"url\":\"/sys/test\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547644831000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":11,\"name\":\"人力管理页面a\",\"aclModuleId\":13,\"url\":\"/sys/testa\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547644844720,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:20:45', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (27, 4, 11, '{\"id\":11,\"code\":\"20190116212031_55\",\"name\":\"人力管理页面a\",\"aclModuleId\":13,\"url\":\"/sys/testa\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547644845000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":11,\"code\":\"20190116212031_55\",\"name\":\"人力管理页面\",\"aclModuleId\":13,\"url\":\"/sys/test\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1547644850457,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-01-16 21:20:51', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (28, 6, 1, '[7,8,9]', '[7,8,9,11]', 'Admin', '2019-01-16 21:21:34', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (29, 6, 1, '[7,8,9,11]', '[7,8,9]', 'Admin', '2019-01-16 21:21:43', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (30, 7, 1, '[2,1]', '[1,2,3,4,5]', 'Admin', '2019-01-16 21:22:09', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (31, 6, 1, '[7,8,9]', '[2,1]', 'Admin', '2019-01-16 21:22:55', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (32, 6, 1, '[2,1]', '[7,8,9,11]', 'Admin', '2019-01-16 21:25:48', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (33, 6, 1, '[7,8,9,11]', '[7,8,9,10,11]', 'Admin', '2019-01-16 21:26:02', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (34, 6, 1, '[7,8,9,10,11]', '[7,8,9,10]', 'Admin', '2019-01-16 21:26:13', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (35, 6, 1, '[7,8,9,10]', '[7,8,9,10,11]', 'Admin', '2019-01-16 21:26:40', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (36, 7, 1, '[1,2,3,4,5]', '[4,5]', 'Admin', '2019-01-16 21:27:20', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (37, 7, 1, '[4,5]', '[1,2,3]', 'Admin', '2019-01-16 21:28:31', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (38, 6, 1, '[7,8,9,10,11]', '[4,5]', 'Admin', '2019-01-16 21:28:39', '0:0:0:0:0:0:0:1', 1);
INSERT INTO `sys_log` VALUES (39, 6, 1, '[4,5]', '[7,8,9,10,11]', 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '角色的类型，1：管理员角色，2：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：可用，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '产品管理员', 1, 1, '', 'Admin', '2019-01-11 20:17:15', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (2, '订单管理员', 1, 1, '', 'Admin', '2019-01-11 19:12:33', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (3, '公告管理员', 1, 1, '', 'Admin', '2019-01-11 19:12:46', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (4, '权限管理员', 1, 1, '', 'Admin', '2019-01-12 21:02:14', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES (5, '运维管理员', 1, 1, '运维', 'Admin', '2019-01-16 11:24:50', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `acl_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次操作的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
INSERT INTO `sys_role_acl` VALUES (43, 1, 7, 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (44, 1, 8, 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (45, 1, 9, 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (46, 1, 10, 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES (47, 1, 11, 'Admin', '2019-01-16 21:30:17', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次操作的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (19, 1, 1, 'Admin', '2019-01-16 21:28:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES (20, 1, 2, 'Admin', '2019-01-16 21:28:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES (21, 1, 3, 'Admin', '2019-01-16 21:28:31', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名称',
  `telephone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `dept_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户所在部门的id',
  `status` int(11) NOT NULL COMMENT '状态。1：正常，0：冻结，2：删除',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `operator` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'Admin', '18612345678', 'admin@qq.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, '第一个用户admin', 'Admin', '2019-01-16 21:05:08', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES (2, 'wangjf', '13299997777', 'wangjf@qq.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, 'wangjf', 'system', '2019-01-09 14:39:12', '127.0.0.1');
INSERT INTO `sys_user` VALUES (3, 'freeman', '13009877890', 'freeman@qq.com', '25D55AD283AA400AF464C76D713C07AD', 2, 1, '', 'Admin', '2019-01-14 11:28:52', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES (4, 'dote', '13288886666', 'dote@qq.com', '25D55AD283AA400AF464C76D713C07AD', 1, 1, 'sss', 'Admin', '2019-01-16 11:16:23', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES (5, '客服小张', '13888889999', 'zhang@qq.com', '25D55AD283AA400AF464C76D713C07AD', 9, 1, '', 'Admin', '2019-01-16 21:17:38', '0:0:0:0:0:0:0:1');

SET FOREIGN_KEY_CHECKS = 1;
