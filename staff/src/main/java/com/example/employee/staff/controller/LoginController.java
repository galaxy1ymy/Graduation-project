package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Login;
import com.example.employee.staff.domain.Staff;
import com.example.employee.common.resp.CommonResp;
import com.example.employee.staff.req.StaffLoginReq;
import com.example.employee.staff.service.LoginService;
import com.example.employee.staff.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private StaffService staffService;


    @PostMapping("/login")
    public CommonResp<Map<String, Object>> login(
            @Valid @RequestBody StaffLoginReq req) {

        // 1️登录校验
        Login login = loginService.login(req);

        // 2️查员工信息
        Staff staff = staffService.getStaffByJobNumber(
                String.valueOf(login.getJobNumber())
        );

        // 3组装返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("jobNumber", login.getJobNumber());
        data.put("role", login.getRole());
        data.put("staffName", staff != null ? staff.getName() : "");

        return new CommonResp<>(data);
    }



}
