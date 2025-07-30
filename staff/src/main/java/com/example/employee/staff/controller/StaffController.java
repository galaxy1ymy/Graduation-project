package com.example.employee.staff.controller;

import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @RequestMapping("/count")
    public Integer count() {
        return staffService.count();
    }
}
