package com.example.employee.staff.DTO;

import lombok.Data;

@Data
public class StaffDTO {
    private Long id;
    private String jobNumber;
    private String name;
    private String gender;      // 添加性别
    private Integer age;        // 添加年龄
    private String phone;       // 添加手机号
    private String department;
    private String position;
    private String status;
}