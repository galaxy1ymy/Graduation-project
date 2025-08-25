

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
    `department` BIGINT comment '部门',
    `position` BIGINT comment '职位',
    `hire_date` DATE comment '入职日期',
    `status` TINYINT DEFAULT 1 comment '状态', -- 1:在职，0:离职
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_job_number_unique` (`id`, `job_number`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工';


