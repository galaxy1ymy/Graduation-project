package com.example.employee.staff.controller;

import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController {

    @Autowired
    private StaffService staffService;
    /*@Autowired
    private LoginService loginService;*/

    @GetMapping("/count")
    public Integer count() {
        return staffService.count();
    }

    @GetMapping("/active/count")
    public Integer activeCount() {
        return staffService.countByStatus("1");
    }


    @GetMapping("/getNames")
    public String getNameByJobNumber(@RequestParam String jobNumber) {  //绑定 HTTP 请求参数 到方法参数的注解
        return staffService.getNameByJobNumber(jobNumber);
    }



}
