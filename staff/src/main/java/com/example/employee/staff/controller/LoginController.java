package com.example.employee.staff.controller;

import com.example.common.resp.CommonResp;
import com.example.employee.staff.domain.Login;
import com.example.employee.staff.req.StaffLoginReq;
import com.example.employee.staff.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public CommonResp<Login> login(@RequestBody StaffLoginReq req){
        Login login = loginService.login(req);
        return new CommonResp<>(login) ;
    }
}
