package com.example.employee.staff.controller;

import com.example.employee.staff.domain.LeaveRequest;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.LeaveRequestService;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public String createLeave(@RequestBody LeaveRequest request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }

        // 附件可选，如果前端没上传就是空字符串或 null
        if (request.getAttachment() == null) {
            request.setAttachment("");
        }

        request.setStatus(0); // 默认待审批
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = leaveRequestService.createLeave(request);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/checkAll")
    public List<LeaveRequest> checkAll(@RequestParam String jobNumber) {
        List<LeaveRequest> list = leaveRequestService.getAllLeaveRequest(jobNumber);
        return list;
    }
}
