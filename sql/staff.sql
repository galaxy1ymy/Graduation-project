

drop table if exists `login`;
CREATE TABLE `login` (
                         `job_number` bigint NOT NULL comment '工号',
                         `password` VARCHAR(50) NOT NULL comment '密码',
                         PRIMARY KEY (`job_number`),
                         UNIQUE KEY `job_number_unique` (`job_number`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录';

drop table if exists `staff`;
CREATE TABLE `staff` (
    `id` BIGINT not null AUTO_INCREMENT comment '序号',
    `job_number` VARCHAR(10) NOT NULL comment '工号',
    `name` VARCHAR(50) NOT NULL comment '姓名',
    `gender` VARCHAR(10) comment '性别',
    `age` INT(3) comment '年龄',
    `phone` VARCHAR(20) comment '手机号',
    `email` VARCHAR(100) comment '邮箱',
    `address` VARCHAR(200) comment '地址',
    `department` VARCHAR(20) comment '部门',
    `position` VARCHAR(20) comment '职位',
    `hire_date` DATE comment '入职日期',
    `status` TINYINT DEFAULT 1 comment '状态', -- 1:在职，0:离职
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_job_number_unique` (`id`, `job_number`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工';

ALTER TABLE staff ADD UNIQUE INDEX uq_job_number(job_number);

drop table if exists `attendance_records`;
CREATE TABLE attendance_records (
    `id` INT AUTO_INCREMENT PRIMARY KEY comment '序号',        -- 唯一ID
    `job_number` VARCHAR(50) NOT NULL comment '工号',          -- 员工编号，关联 staff 表
    `punch_time` DATETIME NOT NULL comment '打卡时间',             -- 打卡时间
    `type` ENUM('上班','下班') DEFAULT '上班' comment '打卡类型', -- 打卡类型，可选，上班/下班
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '创建时间', -- 创建时间
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考勤打卡';

DROP TABLE IF EXISTS `leave_request`;
CREATE TABLE `leave_request` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '请假记录ID',
    `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
    `leave_type` VARCHAR(20) NOT NULL COMMENT '请假类型',
    `start_time` DATETIME NOT NULL COMMENT '请假开始时间',
    `end_time` DATETIME NOT NULL COMMENT '请假结束时间',
    `leave_duration` DECIMAL(5,2) NOT NULL COMMENT '请假时长',
    `reason` TEXT COMMENT '请假原因',
    `attachment` VARCHAR(200) DEFAULT NULL COMMENT '附件说明',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
    `approver_id` BIGINT COMMENT '审批人ID',
    `approve_time` DATETIME COMMENT '审批时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假申请表';


DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '出差记录ID',
    `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
    `reason` TEXT COMMENT '出差事由',
    `start_time` DATETIME NOT NULL COMMENT '出差开始时间',
    `end_time` DATETIME NOT NULL COMMENT '出差结束时间',
    `business_duration` DECIMAL(5,2) NOT NULL COMMENT '出差时长',
    `attachment` VARCHAR(200) DEFAULT NULL COMMENT '附件说明',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
    `approver_id` BIGINT COMMENT '审批人ID',
    `approve_time` DATETIME COMMENT '审批时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出差申请表';


DROP TABLE IF EXISTS `overtime`;
CREATE TABLE `overtime` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '加班记录ID',
    `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
    `reason` TEXT COMMENT '加班事由',
    `start_time` DATETIME NOT NULL COMMENT '加班开始时间',
    `end_time` DATETIME NOT NULL COMMENT '加班结束时间',
    `overtime_duration` DECIMAL(5,2) NOT NULL COMMENT '加班时长',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
    `approver_id` BIGINT COMMENT '审批人ID',
    `approve_time` DATETIME COMMENT '审批时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加班申请表';


DROP TABLE IF EXISTS `going_out`;
CREATE TABLE `going_out` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '外出记录ID',
    `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
    `reason` TEXT COMMENT '外出事由',
    `address` TEXT COMMENT '外出地点',
    `start_time` DATETIME NOT NULL COMMENT '外出开始时间',
    `end_time` DATETIME NOT NULL COMMENT '外出结束时间',
    `goingOut_duration` DECIMAL(5,2) NOT NULL COMMENT '外出时长',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
    `approver_id` BIGINT COMMENT '审批人ID',
    `approve_time` DATETIME COMMENT '审批时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外出申请表';


DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '会议室预定记录ID',
  `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
  `room` TEXT COMMENT '会议室名称',
  `number` TEXT COMMENT '参会人数',
  `name` TEXT COMMENT '参会人员',
  `start_time` DATETIME NOT NULL COMMENT '会议开始时间',
  `end_time` DATETIME NOT NULL COMMENT '会议结束时间',
  `meeting_duration` DECIMAL(5,2) NOT NULL COMMENT '会议时长',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
  `approver_id` BIGINT COMMENT '审批人ID',
  `approve_time` DATETIME COMMENT '审批时间',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室预定申请表';


DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
      `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '物品领取记录ID',
      `job_number` VARCHAR(50) NOT NULL COMMENT '员工ID',
      `use` TEXT COMMENT '用途',
      `name` VARCHAR(50) COMMENT '物品名称',
      `number` BIGINT COMMENT '物品数量',
      `attachment` VARCHAR(200) DEFAULT NULL COMMENT '附件说明',
      `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审批状态：0=待审批，1=已通过，2=已拒绝',
      `approver_id` BIGINT COMMENT '审批人ID',
      `approve_time` DATETIME COMMENT '审批时间',
      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      FOREIGN KEY (job_number) REFERENCES staff(job_number) -- 关联 staff 表
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物品领取申请表';