/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50739
 Source Host           : localhost:3306
 Source Schema         : employee

 Target Server Type    : MySQL
 Target Server Version : 50739
 File Encoding         : 65001

 Date: 10/07/2023 07:53:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL DEFAULT 0,
  `department_id` bigint(20) NOT NULL DEFAULT 0,
  `clock_in_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'clock-in time',
  `clock_out_time` timestamp(0) NULL DEFAULT NULL COMMENT 'clock-out time',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employee_id`(`employee_id`) USING BTREE,
  INDEX `idx_department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考勤表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (12, 1, 1, '2023-07-03 14:07:16', '2023-07-03 14:07:22', '2023-07-03 14:07:16', '2023-07-03 14:07:21', 0);
INSERT INTO `attendance` VALUES (13, 1, 0, '2023-07-06 11:57:43', '2023-07-06 13:41:45', '2023-07-06 11:57:43', '2023-07-06 13:41:44', 0);
INSERT INTO `attendance` VALUES (14, 12, 1, '2023-07-07 16:11:25', NULL, '2023-07-07 16:11:24', '2023-07-07 16:11:24', 0);
INSERT INTO `attendance` VALUES (15, 14, 1, '2023-07-07 16:35:12', NULL, '2023-07-07 16:35:12', '2023-07-07 16:35:12', 0);
INSERT INTO `attendance` VALUES (16, 16, 1, '2023-07-07 17:53:20', NULL, '2023-07-07 17:53:19', '2023-07-07 17:53:19', 0);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_department_id` bigint(20) NULL DEFAULT -1,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '研发部', 0, '2023-06-28 11:11:56', '2023-06-28 11:11:56', 0);
INSERT INTO `department` VALUES (3, '设计部', 0, '2023-06-28 14:44:51', '2023-06-28 14:44:51', 0);
INSERT INTO `department` VALUES (7, '设1部', 0, '2023-06-28 15:22:56', '2023-06-28 15:22:56', 0);
INSERT INTO `department` VALUES (11, '手机部', 0, '2023-06-29 15:43:05', '2023-06-29 15:43:05', 0);
INSERT INTO `department` VALUES (12, '产品部', 11, '2023-06-29 15:44:01', '2023-06-29 15:44:01', 0);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `position_id` bigint(16) NOT NULL DEFAULT -1 COMMENT 'position id to connect to the position table',
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-1' COMMENT 'name of employee',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-1' COMMENT 'email of employee',
  `phone_number` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-1' COMMENT 'phone number of employee',
  `hire_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'hire date of  employee',
  `salary` decimal(16, 2) NOT NULL DEFAULT -1.00 COMMENT 'salary of the employee ',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'automatically generated create time ',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'update time ',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  `level` tinyint(2) NOT NULL DEFAULT -1 COMMENT 'level of the employee like \' P0\' \' P1\'',
  `department_id` bigint(16) NOT NULL DEFAULT -1 COMMENT 'department id',
  `gender` tinyint(2) NOT NULL DEFAULT -1 COMMENT '0:female 1 male  -1:null',
  `identity_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT 'Identity card number',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `union key`(`name`, `phone_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (0, 1, 'abc', '', '123124', '2023-07-03 11:47:12', 0.00, '2023-07-22 11:47:12', '2023-07-07 15:45:51', 0, 0, 0, -1, '0');
INSERT INTO `employee` VALUES (1, 2, '', '', '12341', '2023-07-07 15:13:48', 1000.00, '2023-07-07 15:13:48', '2023-07-07 15:45:48', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 4, '', '', '12432', '2023-07-07 15:45:56', 1000.00, '2023-07-07 15:45:56', '2023-07-07 15:45:56', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 6, '', '', '12421332', '2023-07-07 15:49:54', 1000.00, '2023-07-07 15:49:54', '2023-07-07 15:49:54', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 8, '', '', '1242131231232', '2023-07-07 15:50:20', 1000.00, '2023-07-07 15:50:20', '2023-07-07 15:50:20', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 9, '', '', '124213123122', '2023-07-07 15:51:57', 1000.00, '2023-07-07 15:51:57', '2023-07-07 15:51:57', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 10, '', '', '124122', '2023-07-07 15:52:32', 1000.00, '2023-07-07 15:52:32', '2023-07-07 15:52:32', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 11, '', '', '112413422', '2023-07-07 15:56:02', 1000.00, '2023-07-07 15:56:02', '2023-07-07 15:56:02', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 12, '', '', '112123413422', '2023-07-07 16:09:41', 1000.00, '2023-07-07 16:09:41', '2023-07-07 16:09:41', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 14, '', '', '11212341322', '2023-07-07 16:35:10', 1000.00, '2023-07-07 16:35:10', '2023-07-07 16:35:10', 0, 0, 1, -1, '0');
INSERT INTO `employee` VALUES (1, 16, '', '', '111322', '2023-07-07 17:53:17', 1000.00, '2023-07-07 17:53:17', '2023-07-07 17:53:17', 0, 0, 1, -1, '0');

-- ----------------------------
-- Table structure for employee_department
-- ----------------------------
DROP TABLE IF EXISTS `employee_department`;
CREATE TABLE `employee_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employee_id` bigint(20) NULL DEFAULT NULL,
  `department_id` bigint(20) NULL DEFAULT NULL,
  `start_date` date NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employee_id`(`employee_id`) USING BTREE,
  INDEX `idx_department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employee_position
-- ----------------------------
DROP TABLE IF EXISTS `employee_position`;
CREATE TABLE `employee_position`  (
  `employee_id` bigint(20) NOT NULL,
  `position_id` bigint(20) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`employee_id`, `position_id`, `start_date`) USING BTREE,
  INDEX `idx_employee_id`(`employee_id`) USING BTREE,
  INDEX `idx_department_id`(`position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for leave
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) NOT NULL DEFAULT 0,
  `department_id` bigint(20) NOT NULL DEFAULT 0,
  `start_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'leave start date',
  `end_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'leave end date',
  `leave_type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '0: sick 1: personal 2: annual 年假 3: other',
  `leave_reason` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '请假原因',
  `approver_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'id of approver, approver is the leader of the department',
  `approve_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '0: waiting 1: approved 2: rejected',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_employee_id`(`employee_id`) USING BTREE,
  INDEX `idx_department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for leave_backup
-- ----------------------------
DROP TABLE IF EXISTS `leave_backup`;
CREATE TABLE `leave_backup`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `leave_id` bigint(20) NOT NULL DEFAULT 0,
  `employee_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'For the specified leave_id, employee_id represents who will hand over the work after taking leave',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_leave_id`(`leave_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假交接表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for leave_copy
-- ----------------------------
DROP TABLE IF EXISTS `leave_copy`;
CREATE TABLE `leave_copy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `leave_id` bigint(20) NOT NULL DEFAULT 0,
  `employee_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'For the specified leave_id, employee_id represents who the leave information is copied to',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_leave_id`(`leave_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假抄送表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `position_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '0: not deleted 1: deleted',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (1, 'Java开发实习生', '2023-06-29 15:51:49', '2023-06-29 15:51:49', 0);
INSERT INTO `position` VALUES (2, 'C++开发实习生', '2023-06-29 15:52:05', '2023-06-29 15:52:05', 0);
INSERT INTO `position` VALUES (3, 'C++开发工程师', '2023-06-29 15:52:14', '2023-06-29 15:52:14', 0);
INSERT INTO `position` VALUES (4, '产品经理', '2023-06-29 15:52:20', '2023-06-29 15:52:20', 0);

SET FOREIGN_KEY_CHECKS = 1;
