/*
 Navicat Premium Data Transfer

 Source Server         : huawei
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : 119.3.183.185:3306
 Source Schema         : Restful

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 07/05/2024 21:39:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `userId` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES (1, '哈哈', '哈哈', 'https://img.qovv.cn/2024/05/07/6639db4138b0d.png', 12);
INSERT INTO `content` VALUES (2, '嘎嘎', '嘎嘎', 'https://img2.imgtp.com/2024/05/07/ZdkM1nAz.png', 9);

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `role_id` int(0) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES (1, 1, 1, 'ada');
INSERT INTO `permissions` VALUES (5, 9, 1, 'ss');
INSERT INTO `permissions` VALUES (6, 10, 2, 'ssa');
INSERT INTO `permissions` VALUES (9, 12, 2, 'user');
INSERT INTO `permissions` VALUES (10, 13, 2, 'content');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permission` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '{\"create\": true, \"read\": true, \"update\": true, \"delete\": true}');
INSERT INTO `role` VALUES (2, 'user', '{\"create\": false, \"read\": true, \"update\": false, \"delete\": false}');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contact_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_identity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'ada', '122', 'aa', 'aa', NULL);
INSERT INTO `users` VALUES (9, 'ss', NULL, NULL, '$2a$10$7BuCsKboFEy.P/F5OHhLhunKVnm3CqlqM3yMUMUH022Rd8ePmvt2K', NULL);
INSERT INTO `users` VALUES (10, 'ssa', NULL, NULL, '$2a$10$dhMkHf6Ym6DO6x8ViAGTQuk49uWSdBDtIAGLqOs15XJwxjObAUz16', NULL);
INSERT INTO `users` VALUES (12, 'user', 'user', 'user', '$2a$10$3a2W1enebROym3ei8kibAObjTDNzn/GzMxB.B2rN8QsKFG7IZbUQy', NULL);
INSERT INTO `users` VALUES (13, 'content', 'content', 'content', '$2a$10$olA/CBfAZW5cUPtZYizZ..45AR5.pSdlvJohjAbnKiF4o8GcKBE5G', NULL);

SET FOREIGN_KEY_CHECKS = 1;
