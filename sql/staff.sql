CREATE TABLE staff (
    id BIGINT not null  comment '员工ID',
    name VARCHAR(50) NOT NULL comment '姓名',
    gender VARCHAR(10) comment '性别',
    age INT(3) comment '年龄',
    phone VARCHAR(20) comment '手机号',
    email VARCHAR(100) comment '邮箱',
    department_id BIGINT comment '部门ID',
    position BIGINT comment '职位',
    hire_date DATE comment '入职日期',
    status TINYINT DEFAULT 1 comment '状态', -- 1:在职，0:离职
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP comment '更新时间',
    PRIMARY KEY (id),
    unique key `id` (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工';
