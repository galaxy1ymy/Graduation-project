package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Overtime;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.OvertimeService;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/overtime")
public class OvertimeController {

    @Autowired
    private OvertimeService overtimeService;

    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public String createOvertime(@RequestBody Overtime request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }
        request.setStatus(0); // 默认待审批
        request.setName(staff.getName());
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = overtimeService.createOvertime(request);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/checkAll")
    public List<Overtime> checkAll(@RequestParam String jobNumber) {
        List<Overtime> list = overtimeService.getAllOvertime(jobNumber);
        return list;
    }

    // 新增审批接口
    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody Overtime request) {
        Overtime overtime = overtimeService.getOvertimeById(id);
        if (overtime == null) return "fail: 记录不存在";

        overtime.setStatus(request.getStatus()); // 1=通过，2=驳回
        overtime.setApproverId(request.getApproverId()); // 可选
        overtime.setApproveTime(new Date());
        overtime.setUpdateTime(new Date());

        int result = overtimeService.updateOvertime(overtime);
        return result > 0 ? "success" : "fail";
    }
}
