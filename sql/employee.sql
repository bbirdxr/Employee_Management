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

 Date: 30/06/2023 14:16:10
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `employee_id`     bigint(20) NOT NULL DEFAULT 0 COMMENT '员工 id',
    `department_id`   bigint(20) NOT NULL DEFAULT 0 COMMENT '部门 id',
    `attendance_date` date NULL DEFAULT NULL COMMENT '考勤日期',
    `clock_in_time`   timestamp(0) NULL DEFAULT NULL COMMENT '上班打卡时间',
    `clock_out_time`  timestamp(0) NULL DEFAULT NULL COMMENT '下班打卡时间',
    `create_time`     timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `idx_employee_id`(`employee_id`) USING BTREE,
    INDEX             `idx_department_id`(`department_id`) USING BTREE,
    INDEX             `idx_attendance_date`(`attendance_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考勤表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `department_name`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `parent_department_id` bigint(20) NULL DEFAULT -1,
    `create_time`          timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`          timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department`
VALUES (1, '研发部', 0, '2023-06-28 11:11:56', '2023-06-28 11:11:56');
INSERT INTO `department`
VALUES (3, '设计部', 0, '2023-06-28 14:44:51', '2023-06-28 14:44:51');
INSERT INTO `department`
VALUES (7, '设1部', 0, '2023-06-28 15:22:56', '2023-06-28 15:22:56');
INSERT INTO `department`
VALUES (11, '手机部', 0, '2023-06-29 15:43:05', '2023-06-29 15:43:05');
INSERT INTO `department`
VALUES (12, '产品部', 11, '2023-06-29 15:44:01', '2023-06-29 15:44:01');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`
(
    `position_id`   bigint(20) NOT NULL,
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `email`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `phone_number`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `hire_date`     date NULL DEFAULT NULL,
    `salary`        decimal(10, 2) NULL DEFAULT NULL,
    `create_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    `level`         tinyint(2) NOT NULL,
    `department_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee`
VALUES (1, 1, '王五', '485189519@baidu.com', '123456789', '2023-06-28', 200.00, '2023-06-28 10:02:33',
        '2023-06-29 16:48:38', 0, 1);
INSERT INTO `employee`
VALUES (1, 2, '但是', '48632@qq.com', '', NULL, 300.00, '2023-06-29 11:56:33', '2023-06-29 16:48:39', 0, 1);
INSERT INTO `employee`
VALUES (1, 3, '但是', '', '', NULL, 0.00, '2023-06-29 16:03:55', '2023-06-29 16:05:16', 0, 12);

-- ----------------------------
-- Table structure for employee_department
-- ----------------------------
DROP TABLE IF EXISTS `employee_department`;
CREATE TABLE `employee_department`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `employee_id`   bigint(20) NULL DEFAULT NULL,
    `department_id` bigint(20) NULL DEFAULT NULL,
    `start_date`    date NULL DEFAULT NULL,
    `end_date`      date NULL DEFAULT NULL,
    `create_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `idx_employee_id`(`employee_id`) USING BTREE,
    INDEX           `idx_department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employee_position
-- ----------------------------
DROP TABLE IF EXISTS `employee_position`;
CREATE TABLE `employee_position`
(
    `employee_id` bigint(20) NOT NULL,
    `position_id` bigint(20) NOT NULL,
    `start_date`  date         NOT NULL,
    `end_date`    date NULL DEFAULT NULL,
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`employee_id`, `position_id`, `start_date`) USING BTREE,
    INDEX         `idx_employee_id`(`employee_id`) USING BTREE,
    INDEX         `idx_department_id`(`position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for leave
-- ----------------------------
DROP TABLE IF EXISTS `leave`;
CREATE TABLE `leave`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `employee_id`    bigint(20) NOT NULL DEFAULT 0 COMMENT '员工 id',
    `department_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '部门 id',
    `start_date`     date NULL DEFAULT NULL COMMENT '开始日期',
    `end_date`       date NULL DEFAULT NULL COMMENT '结束日期',
    `leave_type`     tinyint(3) NOT NULL DEFAULT 0 COMMENT '请假类型',
    `leave_reason`   varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '请假原因',
    `approver_id`    bigint(20) NOT NULL DEFAULT 0 COMMENT '审批人 id',
    `approve_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '请假状态',
    `create_time`    timestamp(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp(0)                                             NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_employee_id`(`employee_id`) USING BTREE,
    INDEX            `idx_department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请假表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `position_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '职位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position`
VALUES (1, 'Java开发实习生', '2023-06-29 15:51:49', '2023-06-29 15:51:49');
INSERT INTO `position`
VALUES (2, 'C++开发实习生', '2023-06-29 15:52:05', '2023-06-29 15:52:05');
INSERT INTO `position`
VALUES (3, 'C++开发工程师', '2023-06-29 15:52:14', '2023-06-29 15:52:14');
INSERT INTO `position`
VALUES (4, '产品经理', '2023-06-29 15:52:20', '2023-06-29 15:52:20');

SET
FOREIGN_KEY_CHECKS = 1;
