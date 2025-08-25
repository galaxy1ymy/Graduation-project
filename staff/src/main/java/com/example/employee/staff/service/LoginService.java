package com.example.employee.staff.service;

import com.example.employee.staff.domain.Login;
import com.example.employee.staff.mapper.LoginMapper;
import com.example.employee.staff.req.StaffLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public Login login(StaffLoginReq  req) {
        Long jobNumber = req.getJobNumber();
        String password = req.getPassword();
        Login login = loginMapper.selectByPrimaryKey(jobNumber);
        if (login == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!login.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        return login;
    }

}
