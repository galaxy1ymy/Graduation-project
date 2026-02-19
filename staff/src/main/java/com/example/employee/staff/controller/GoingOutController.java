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
        request.setName(staff.getName());
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

    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody GoingOut request) {
        GoingOut goingOut = goingOutService.getGoingOutById(id);
        if (goingOut == null) {
            return "fail: 记录不存在";
        }

        goingOut.setStatus(request.getStatus()); // 1 = 通过，2 = 驳回
        goingOut.setApproverId(request.getApproverId()); // 可选
        goingOut.setApproveTime(new Date());
        goingOut.setUpdateTime(new Date());

        int result = goingOutService.updateGoingOut(goingOut);
        return result > 0 ? "success" : "fail";
    }
}
