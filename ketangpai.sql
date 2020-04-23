/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : ketangpai

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-04-23 13:51:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` varchar(6) NOT NULL,
  `courseName` varchar(255) DEFAULT NULL,
  `className` varchar(255) DEFAULT NULL,
  `teacherId` int(11) DEFAULT NULL,
  `term` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1wsed4', 'Java EE架构与程序设计(1,2班)', '117030801,2,3,4', '1', '2019-2020,第一学期');
INSERT INTO `course` VALUES ('gih67s', 'uml', '117030802', '1', '2019-2020,第一学期');
INSERT INTO `course` VALUES ('khpdur', '一趟', '385', '1', '2013-2014,第二学期');
INSERT INTO `course` VALUES ('n33elb', '数据结构', '117030801', '1', '2018-2019,第一学期');
INSERT INTO `course` VALUES ('ova5i4', 'GGGGGG', 'GGG', '1', '2019-2020,第一学期');
INSERT INTO `course` VALUES ('q23dd4', '高等数学', '382', '5', '2018-2019,无限制');
INSERT INTO `course` VALUES ('t9sw4w', 'Algorithms-12班', '117030801、02', '1', '2019-2020,第一学期');

-- ----------------------------
-- Table structure for coursestudent
-- ----------------------------
DROP TABLE IF EXISTS `coursestudent`;
CREATE TABLE `coursestudent` (
  `courseId` varchar(255) NOT NULL,
  `studentId` int(255) NOT NULL,
  `isFile` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coursestudent
-- ----------------------------
INSERT INTO `coursestudent` VALUES ('t9sw4w', '2', '0');
INSERT INTO `coursestudent` VALUES ('1wsed4', '2', '0');
INSERT INTO `coursestudent` VALUES ('gih67s', '2', '0');
INSERT INTO `coursestudent` VALUES ('1wsed4', '3', '0');
INSERT INTO `coursestudent` VALUES ('q23dd4', '1', '0');
INSERT INTO `coursestudent` VALUES ('1wsed4', '6', '0');

-- ----------------------------
-- Table structure for courseteacher
-- ----------------------------
DROP TABLE IF EXISTS `courseteacher`;
CREATE TABLE `courseteacher` (
  `courseId` varchar(255) NOT NULL,
  `teacherId` int(11) NOT NULL,
  `isFile` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of courseteacher
-- ----------------------------
INSERT INTO `courseteacher` VALUES ('n33elb', '1', '0');
INSERT INTO `courseteacher` VALUES ('gih67s', '1', '0');
INSERT INTO `courseteacher` VALUES ('1wsed4', '1', '0');
INSERT INTO `courseteacher` VALUES ('t9sw4w', '1', '0');
INSERT INTO `courseteacher` VALUES ('q23dd4', '5', '0');
INSERT INTO `courseteacher` VALUES ('khpdur', '1', '0');
INSERT INTO `courseteacher` VALUES ('ova5i4', '1', '0');

-- ----------------------------
-- Table structure for homework
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseId` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `file` longtext,
  `createTime` datetime DEFAULT NULL,
  `canDelay` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', '1wsed4', 'javaee大作业', '完完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交完成大作业并提交成大作业并提交', '2019-12-17 20:43:00', '10', 'http://localhost:8080/ketangpai/(1576995650923)2019-2020学年学生心理健康建档测评流程.docx,http://localhost:8080/ketangpai/(ssssssssssssss)新建 DOCX 文档.docx', '2019-12-13 12:07:10', '1');
INSERT INTO `homework` VALUES ('26', '1wsed4', 'k', '<p>kk<img src=\"http://localhost:8080/ketangpai/(1577194431256)部署图.jpg\" style=\"max-width: 100%;\"></p>', '2019-12-04 12:00:00', '10', 'http://localhost:8080/ketangpai/(1577194431256)部署图.jpg', '2019-12-24 21:34:05', '0');

-- ----------------------------
-- Table structure for homework1
-- ----------------------------
DROP TABLE IF EXISTS `homework1`;
CREATE TABLE `homework1` (
  `id` int(255) DEFAULT NULL,
  `schoolId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `isSubmit` int(11) NOT NULL DEFAULT '0',
  `isRead` int(11) NOT NULL DEFAULT '0',
  `file` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework1
-- ----------------------------
INSERT INTO `homework1` VALUES ('2', '11703080102', '林远图', '1', '2019-12-24 11:39:05', '1', '5', 'http://localhost:8080/ketangpai/(1577158740905)mybatis-demo.zip,http://localhost:8080/ketangpai/(ssssssssssssss)新建 DOCX 文档.docx');
INSERT INTO `homework1` VALUES ('3', '11703080103', '张麻子', null, null, '0', '0', null);
INSERT INTO `homework1` VALUES ('6', '13152369', '李特基', null, '2019-12-30 19:43:24', '1', '0', 'http://localhost:8080/ketangpai/(1577706203185)教师确认顺序图.jpg');

-- ----------------------------
-- Table structure for homework26
-- ----------------------------
DROP TABLE IF EXISTS `homework26`;
CREATE TABLE `homework26` (
  `file` longtext,
  `score` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `isSubmit` int(11) NOT NULL DEFAULT '0',
  `isRead` int(11) NOT NULL DEFAULT '0',
  `id` int(11) DEFAULT NULL,
  `schoolId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework26
-- ----------------------------
INSERT INTO `homework26` VALUES ('http://localhost:8080/ketangpai/(1577682341389)学生领票顺序图.jpg', '8', '2019-12-30 13:05:42', '1', '1', '2', '11703080102', '林远图');
INSERT INTO `homework26` VALUES (null, null, null, '0', '0', '3', '11703080103', '张麻子');
INSERT INTO `homework26` VALUES (null, null, null, '0', '0', '6', '13152369', '李特基');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `nameZh` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'teacher', '教师');
INSERT INTO `role` VALUES ('2', 'student', '学生');
INSERT INTO `role` VALUES ('3', 'guest', '游客');

-- ----------------------------
-- Table structure for ts
-- ----------------------------
DROP TABLE IF EXISTS `ts`;
CREATE TABLE `ts` (
  `id` int(11) NOT NULL,
  `tsid` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `userFace` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts
-- ----------------------------
INSERT INTO `ts` VALUES ('1', null, '重庆理工大学', '刘洁', null);
INSERT INTO `ts` VALUES ('2', '11703080102', '重庆理工大学', '林远图', null);
INSERT INTO `ts` VALUES ('3', '11703080103', '重庆理工大学', '张麻子', null);
INSERT INTO `ts` VALUES ('5', '', '重庆工商大学', '刘新宇', null);
INSERT INTO `ts` VALUES ('6', '13152369', '重庆大学', '李特基', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'teacher', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('2', 'student1', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('3', 'student2', '$2a$10$RMuFXGQ5AtH4wOvkUqyvuecpqUSeoxZYqilXzbz50dceRsga.WYiq', '1', '0');
INSERT INTO `user` VALUES ('5', '491910252@qq.com', '$2a$10$wv4rXeduqrLntX6Wn.sgwesanIFWQbFGY.dIcQUobFXkCO.DD8GUa', '1', '0');
INSERT INTO `user` VALUES ('6', '789456123@qq.com', '$2a$10$e1AWoji.mlghUzBS8iAvhulKU3WB48aj5E9zYMAlYfHAi3/laxZaa', '1', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '3', '2');
INSERT INTO `user_role` VALUES ('4', '5', '1');
INSERT INTO `user_role` VALUES ('5', '6', '2');
