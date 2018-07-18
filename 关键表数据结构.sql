/*
Navicat MySQL Data Transfer

Source Server         : idea本地dev分支合并测试开发修改
Source Server Version : 50637
Source Host           : 10.37.251.224:3310
Source Database       : qdp_oasis
Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001
加一行
Date: 2018-07-17  10:51:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oas_account_odd
-- ----------------------------
DROP TABLE IF EXISTS `oas_account_odd`;
CREATE TABLE `oas_account_odd` (
  `account_id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '应收零头id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`account_id`),
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1329 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收零头账户';

-- ----------------------------
-- Table structure for oas_account_payable
-- ----------------------------
DROP TABLE IF EXISTS `oas_account_payable`;
CREATE TABLE `oas_account_payable` (
  `account_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '应付账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `building_id` varchar(30) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '楼栋id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `supplier_id` varchar(30) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '供应商id',
  `room_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '房间id',
  `fee_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '收费项目',
  `link_type` int(4) NOT NULL DEFAULT '0' COMMENT '业务对象',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `is_history` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否为历史账户',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`account_id`),
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE,
  KEY `idx_building_id` (`building_id`) USING BTREE,
  KEY `idx_fee_id` (`fee_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1003050 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应付账户表';

-- ----------------------------
-- Table structure for oas_account_prepay
-- ----------------------------
DROP TABLE IF EXISTS `oas_account_prepay`;
CREATE TABLE `oas_account_prepay` (
  `account_id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '预付账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `supplier_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '供应商id',
  `balance` decimal(18,2) NOT NULL COMMENT '余额',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`account_id`),
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1347 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预付账户';

-- ----------------------------
-- Table structure for oas_account_prestore
-- ----------------------------
DROP TABLE IF EXISTS `oas_account_prestore`;
CREATE TABLE `oas_account_prestore` (
  `account_id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '预存账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`account_id`),
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1353 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预存账户';

-- ----------------------------
-- Table structure for oas_account_rece
-- ----------------------------
DROP TABLE IF EXISTS `oas_account_rece`;
CREATE TABLE `oas_account_rece` (
  `account_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '应收账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `building_id` varchar(30) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '楼栋id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `person_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '人员姓名',
  `room_accno` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '合同id',
  `room_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '房间id',
  `room_sign` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '房间编号',
  `fee_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '收费项目',
  `link_type` int(4) NOT NULL DEFAULT '0' COMMENT '业务对象',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应收余额',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `is_history` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否为历史账户',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`account_id`),
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE,
  KEY `idx_building_id` (`building_id`) USING BTREE,
  KEY `idx_room_accno` (`room_accno`) USING BTREE,
  KEY `idx_fee_id` (`fee_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8753 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账户表';

-- ----------------------------
-- Table structure for oas_bill_payable
-- ----------------------------
DROP TABLE IF EXISTS `oas_bill_payable`;
CREATE TABLE `oas_bill_payable` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '账单id',
  `payable_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应付账单单据号',
  `payable_type` int(2) NOT NULL DEFAULT '0' COMMENT '应付类型 0 暂收应付 1 代收应付 2 垫付应付',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) unsigned NOT NULL DEFAULT '0' COMMENT '账户id',
  `std_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '收费标准',
  `price` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '单价',
  `num` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '数量',
  `payable_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应付金额',
  `begin_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '费用开始时间',
  `end_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '费用结束时间',
  `unpayable_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '未付金额',
  `is_freeze` int(2) NOT NULL DEFAULT '0' COMMENT '是否冻结 : 0 解冻、1 冻结、2 银行代扣、3 地产垫付应付 ',
  `payable_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '应付日期',
  `operator` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '操作人',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '费用备注',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0，否，1：作废/删除',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  `cancel_reason` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '作废原因',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `apply_code` varchar(30) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '申请单号',
  `bill_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应收账单单据号，生成应付的应收账单id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payable_code` (`payable_code`) USING BTREE COMMENT 'payable_code 业务主键',
  KEY `idx_account_id` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2848 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应付账单表';

-- ----------------------------
-- Table structure for oas_bill_rece
-- ----------------------------
DROP TABLE IF EXISTS `oas_bill_rece`;
CREATE TABLE `oas_bill_rece` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '账单id',
  `bill_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账单单据号',
  `bill_type` int(2) NOT NULL DEFAULT '0' COMMENT '账单类型 0 正常应收、 1 转入应收、 2 暂收应收、 3 代收应收、4 垫付应收、5 代付转入',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) unsigned NOT NULL DEFAULT '0' COMMENT '账户id',
  `std_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '收费标准',
  `price` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '单价',
  `num` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '数量',
  `rece_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应收金额',
  `begin_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '费用开始时间',
  `end_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '费用结束时间',
  `bill_sign` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账单签名',
  `debt_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '欠费金额',
  `is_freeze` int(2) NOT NULL DEFAULT '0' COMMENT '是否冻结 : 0 解冻、1 冻结、2 银行代扣、3 地产垫付应付 ',
  `rece_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '应收日期',
  `operator` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '操作人',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '费用备注',
  `calc_desc` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账单描述，记录账单计算过程',
  `dedit_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '逾期日期',
  `dedit_sart_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '违约金起计日期',
  `discount_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '折扣方案ID',
  `discount_amount` decimal(16,2) NOT NULL DEFAULT '0.00' COMMENT '折扣金额',
  `audit_status` int(2) NOT NULL DEFAULT '0' COMMENT '审核状态：0：审核通过/无需审核  1：待审核',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0，否，1：作废/删除',
  `task_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建账户的任务id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  `cancel_reason` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '作废原因',
  `is_replenish` int(2) NOT NULL DEFAULT '0' COMMENT '作废的账单是否补录 0、未补录；1、已补录待审核 2、已补录',
  `channel` int(2) NOT NULL DEFAULT '0' COMMENT '账单渠道',
  `business_refer` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '业务id',
  `cushion_paid` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '垫付应收标识',
  `substitute_paid` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '代付应收标识',
  `substitute_paid_type` int(2) NOT NULL DEFAULT '0' COMMENT '代付类型 0 自付、 1 代付、 2 地产赠券、 3 已售款未清',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_id` (`bill_id`) USING BTREE COMMENT 'bill_id 业务主键',
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_bill_sign` (`bill_sign`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6055 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账单表';

-- ----------------------------
-- Table structure for oas_fee_item
-- ----------------------------
DROP TABLE IF EXISTS `oas_fee_item`;
CREATE TABLE `oas_fee_item` (
  `fee_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '费项id',
  `refer_id` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '映射：收费系统费项id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `fee_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '费项名称',
  `cost_type` int(4) NOT NULL DEFAULT '0' COMMENT '费用性质 1:收入 2:代收 3:暂收',
  `classify` int(4) NOT NULL DEFAULT '0' COMMENT '费用类别 1:临停车费',
  `rule_biz` int(2) NOT NULL DEFAULT '0' COMMENT '业务规则 1:物业费类 2:车位管理费类 3:上门服务类 4:实时缴费类',
  `bill_cycle` int(2) NOT NULL DEFAULT '1' COMMENT '计费周期，1到12个月',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收费项目说明',
  `allow_input` int(1) NOT NULL DEFAULT '0' COMMENT '是否允许输入  0:是  1:否',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态  0:启用  1:停用',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 1:是 0:否',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`fee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123123159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费项目表';

-- ----------------------------
-- Table structure for oas_fee_standard
-- ----------------------------
DROP TABLE IF EXISTS `oas_fee_standard`;
CREATE TABLE `oas_fee_standard` (
  `std_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '标准id',
  `fee_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '费项id',
  `refer_id` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '映射：收费系统标准id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `std_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标准名称',
  `std_code` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标准编号',
  `std_desc` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '标准说明',
  `calc_mode` int(4) NOT NULL DEFAULT '0' COMMENT '计算方式 1:固定 2:阶梯 3:区间 4:金额',
  `ladder` int(4) NOT NULL DEFAULT '0' COMMENT '阶梯 1:分段 2:拉通',
  `std_price` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '收费标准单价',
  `free_unit` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '免费单元数量(临停单次免费时间:分钟)',
  `free_num_unit` int(2) NOT NULL DEFAULT '0' COMMENT '免费数量标准量纲单位 0 无，1 小时、2 分钟、3天、4 平方米、5 元、6 件、7 次、8 楼层、9 单元、10 公斤、11 克、12 吨、13 度，默认为无',
  `free_mode` int(2) NOT NULL DEFAULT '0' COMMENT '免费方式  0：超免费标准免费 1：超免费标准不免费',
  `quota_day` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '日限额',
  `quota_mon` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '月限额',
  `effective_date` datetime NOT NULL DEFAULT '3000-01-01 00:00:00' COMMENT '生效日期',
  `expiry_date` datetime NOT NULL DEFAULT '3000-01-01 00:00:00' COMMENT '失效日期',
  `rece_unit` int(2) NOT NULL DEFAULT '0' COMMENT '应收款取舍位数 1 元、2 角、0 分，默认为分',
  `round_mode` int(2) NOT NULL DEFAULT '0' COMMENT '数量取整方式  0 数量不做取整、 1：数量取整到0.5、2：数量取整到1 、3 ： 数量取整到5、 4 ： 数量取整到10',
  `ladder_type` int(2) NOT NULL DEFAULT '0' COMMENT '阶梯条件类型 0 整数、1：24小时整数、2：包含小数',
  `interval_type` int(2) NOT NULL DEFAULT '0' COMMENT '区间条件类型 0： 时间、1：整数 、2：包含小数',
  `bill_cycle` int(2) NOT NULL DEFAULT '1' COMMENT '计费周期，1到12个月',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态  0:启用  1:停用',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 1:是 0:否',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`std_id`),
  KEY `idx_fee_id` (`fee_id`) USING BTREE,
  KEY `idx_std_name` (`std_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10000198 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收费标准表';

-- ----------------------------
-- Table structure for oas_record_calls
-- ----------------------------
DROP TABLE IF EXISTS `oas_record_calls`;
CREATE TABLE `oas_record_calls` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `bill_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账单id',
  `telephone` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户手机号',
  `calls_type` int(2) NOT NULL DEFAULT '0' COMMENT '催缴类型 0，短信',
  `calls_content` varchar(1024) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '记录内容',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0，否，1：作废/删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='催缴记录表';

-- ----------------------------
-- Table structure for oas_record_derate
-- ----------------------------
DROP TABLE IF EXISTS `oas_record_derate`;
CREATE TABLE `oas_record_derate` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `derate_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '单据号',
  `organ_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '减免金额',
  `bill_id` varchar(30) NOT NULL DEFAULT '' COMMENT '应收账单id',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '减免原因',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0:否 1:是',
  `remarks` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `qd_order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '千丁订单Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_derate_code` (`derate_code`) USING BTREE,
  KEY `idx_bill_id` (`bill_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COMMENT='减免记录表';

-- ----------------------------
-- Table structure for oas_record_paid
-- ----------------------------
DROP TABLE IF EXISTS `oas_record_paid`;
CREATE TABLE `oas_record_paid` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `paid_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收据单id',
  `account_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '预存账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `real_amount` decimal(18,2) NOT NULL COMMENT '实收金额',
  `pay_mode` int(2) NOT NULL DEFAULT '0' COMMENT '结算方式：1、现金，2、支票，3、pos刷卡，4、转账，5、银行代扣，6、千丁，7、千丁临停，8、微信支付，9、银联支付，10、空置房转账',
  `note_title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '支/汇票抬头',
  `note_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '支/汇票号',
  `bank_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户银行名称',
  `bank_account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '银行账号',
  `payers` varchar(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '缴款人',
  `paid_date` date NOT NULL DEFAULT '3000-01-01' COMMENT '收款日期',
  `is_check` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '是否对账',
  `is_del` int(2) NOT NULL COMMENT '是否删除 0 ：否 1：删除',
  `is_cancel` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否作废  0 不作废 1作废',
  `cancel_reason` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '作废原因',
  `dedit_rece` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '违约金应收',
  `dedit_real` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '违约金实收',
  `dedit_derate` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '违约金减免',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `qd_order_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '千丁订单Id',
  `note_use_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '对应的发票使用id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_paid_code` (`paid_code`) USING BTREE,
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=594 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收款单据表';

-- ----------------------------
-- Table structure for oas_record_payment
-- ----------------------------
DROP TABLE IF EXISTS `oas_record_payment`;
CREATE TABLE `oas_record_payment` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `payment_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '付款记录id',
  `account_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '预付账户id',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `region_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小区id',
  `person_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户id',
  `supplier_id` varchar(30) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '供应商id',
  `payment_type` int(2) NOT NULL DEFAULT '0' COMMENT '付款类型：1、预存退款',
  `payment_amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '付款金额',
  `pay_mode` int(2) NOT NULL DEFAULT '0' COMMENT '结算方式：1、现金，2、支票，3、pos刷卡，4、转账，5、银行代扣，6、千丁，7、千丁临停，8、微信支付，9、银联支付，10、空置房转账',
  `note_title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '支/汇票抬头',
  `note_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '支/汇票号',
  `bank_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户银行名称',
  `bank_account` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '客户银行账号',
  `remarks` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_cancel` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否作废 0 不作废 1作废',
  `cancel_reason` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '作废原因',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废 0:否 1:是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_code` (`payment_code`) USING BTREE,
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_region_id` (`region_id`) USING BTREE,
  KEY `idx_person_id` (`person_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='付款记录表';

-- ----------------------------
-- Table structure for oas_sn_odd
-- ----------------------------
DROP TABLE IF EXISTS `oas_sn_odd`;
CREATE TABLE `oas_sn_odd` (
  `odd_sn` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '应收零头流水id',
  `biz_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '交易码',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '零头账户id',
  `business_type` int(5) NOT NULL COMMENT '业务类型：2:零头核销应收(+) 、3:预存核销零头(-) 、10核销冲正（+/-）',
  `amount` decimal(18,2) NOT NULL COMMENT '发生额',
  `balance` decimal(18,2) NOT NULL COMMENT '余额',
  `transfer_sn` bigint(19) NOT NULL COMMENT '转账id',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0：否 1：删除',
  `bill_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应收账单id',
  `paid_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收款单据id',
  `batch_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '批次编号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  PRIMARY KEY (`odd_sn`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_transfer_sn` (`transfer_sn`) USING BTREE,
  KEY `idx_bill_id` (`bill_id`) USING BTREE,
  KEY `idx_paid_code` (`paid_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收零头流水';

-- ----------------------------
-- Table structure for oas_sn_payable
-- ----------------------------
DROP TABLE IF EXISTS `oas_sn_payable`;
CREATE TABLE `oas_sn_payable` (
  `payable_sn` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '应付流水号',
  `biz_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '交易码',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) unsigned NOT NULL DEFAULT '0' COMMENT '账户id',
  `business_type` int(5) NOT NULL COMMENT '业务类型：',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '发生额',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `payable_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应付账单号',
  `transfer_sn` bigint(19) NOT NULL DEFAULT '0' COMMENT '转账流水号',
  `payment_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '付款记录号',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除: 0、否 1、删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  `batch_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '批次编号',
  PRIMARY KEY (`payable_sn`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_payable_code` (`payable_code`) USING BTREE,
  KEY `idx_transfer_sn` (`transfer_sn`) USING BTREE,
  KEY `idx_payment_code` (`payment_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3265 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应付账户流水';

-- ----------------------------
-- Table structure for oas_sn_prepay
-- ----------------------------
DROP TABLE IF EXISTS `oas_sn_prepay`;
CREATE TABLE `oas_sn_prepay` (
  `prepay_sn` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '预付流水id',
  `biz_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '交易码',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '账户id',
  `business_type` int(5) NOT NULL DEFAULT '0' COMMENT '业务类型：6:预存退款(+)、8：付款（-）、13：付款冲正（+）',
  `transfer_sn` bigint(19) NOT NULL DEFAULT '0' COMMENT '转账流水号',
  `payment_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '预付记录id',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '发生额',
  `balance` decimal(18,2) NOT NULL DEFAULT '2.00' COMMENT '余额',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0:否 1:是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注信息',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  `batch_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '批次编号',
  PRIMARY KEY (`prepay_sn`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_transfer_sn` (`transfer_sn`) USING BTREE,
  KEY `idx_payment_code` (`payment_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=517 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预付账户流水';

-- ----------------------------
-- Table structure for oas_sn_prestore
-- ----------------------------
DROP TABLE IF EXISTS `oas_sn_prestore`;
CREATE TABLE `oas_sn_prestore` (
  `prestore_sn` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '预存流水号',
  `biz_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '交易码',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) NOT NULL DEFAULT '0' COMMENT '账户id',
  `business_type` int(5) NOT NULL DEFAULT '0' COMMENT '业务类行：1:预存核销应收(-)、3:预存核销零头(-)、5:预存收款(+)、6:预存退款(-)、9:收款核销（-）10：核销冲正（+）、12收款冲正（-）',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '发生额',
  `balance` decimal(18,2) NOT NULL COMMENT '余额',
  `transfer_sn` bigint(19) unsigned NOT NULL DEFAULT '0' COMMENT '转账流水号',
  `paid_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收款单据号',
  `batch_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '批次编号',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0:否 1：是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  PRIMARY KEY (`prestore_sn`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_paid_code` (`paid_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1434 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预存账户流水';

-- ----------------------------
-- Table structure for oas_sn_rece
-- ----------------------------
DROP TABLE IF EXISTS `oas_sn_rece`;
CREATE TABLE `oas_sn_rece` (
  `rece_sn` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '应收流水号',
  `biz_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '交易码',
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `account_id` bigint(19) unsigned NOT NULL DEFAULT '0' COMMENT '账户id',
  `business_type` int(5) NOT NULL COMMENT '业务类型：1:预存核销应收(-)、2:零头核销应收(-)、4:减免核销(-) 、7:应收账单创建(+) 、9:收款核销（-）10:核销冲正（+）11:账单冲正（-）24:轧差交收核销',
  `amount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '发生额',
  `balance` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `bill_id` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '账单id',
  `transfer_sn` bigint(19) NOT NULL DEFAULT '0' COMMENT '转账流水号',
  `derate_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '减免记录号',
  `paid_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '收款单据号',
  `batch_code` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '批次编号',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除: 0、否 1、删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `creator_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人名称',
  `updater_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `updater_name` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '更新人名称',
  `remarks` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_cancel` int(2) NOT NULL DEFAULT '0' COMMENT '是否作废,0:不作废 1:作废',
  PRIMARY KEY (`rece_sn`),
  KEY `idx_account_id` (`account_id`) USING BTREE,
  KEY `idx_bill_id` (`bill_id`) USING BTREE,
  KEY `idx_transfer_sn` (`transfer_sn`) USING BTREE,
  KEY `idx_derate_code` (`derate_code`) USING BTREE,
  KEY `idx_paid_code` (`paid_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4731 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应收账户流水';


CREATE TABLE `oas_request_identify` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `organ_id` varchar(19) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '租户id',
  `request_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求标识id',
  
  `request_info` varchar(5120) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数信息',
  `request_status` int(2) NOT NULL DEFAULT '0' COMMENT '请求状态 1:待处理 2:处理中 3:处理成功 4:处理失败',
  `error_msg` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '处理失败时的错误信息',
  `request_method` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法',
  `request_result` varchar(5120) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求结果',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除 0否，1删除',
  
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_request_id` (`request_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='请求参数标识表';
