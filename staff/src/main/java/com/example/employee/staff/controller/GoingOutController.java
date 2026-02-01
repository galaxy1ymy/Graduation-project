package com.example.employee.staff.controller;

import com.example.employee.staff.domain.GoingOut;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.GoingOutService;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goingOut")
public class GoingOutController {
    @Autowired
    private GoingOutService outService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private GoingOutService goingOutService;

    @PostMapping("/create")
    public String createOut(@RequestBody GoingOut request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }
        request.setStatus(0); // 默认待审批
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = outService.createGoingOut (request);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/checkAll")
    public List<GoingOut> checkAll(@RequestParam String jobNumber) {
        List<GoingOut> list = goingOutService.getAllGoingOut(jobNumber);
        return list;
    }
}
