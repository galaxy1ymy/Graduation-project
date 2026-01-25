package com.example.employee.manager.employee.staff.controller;

import com.example.employee.manager.employee.staff.domain.Meeting;
import com.example.employee.manager.employee.staff.domain.Staff;
import com.example.employee.manager.employee.staff.service.MeetingService;
import com.example.employee.manager.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public String createMeeting(@RequestBody Meeting request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }

        request.setStatus(0); // 默认待审批
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = meetingService.createMeeting(request);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/checkAll")
    public List<Meeting> checkAll(@RequestParam String jobNumber) {
        List<Meeting> list = meetingService.getAllMeeting(jobNumber);
        return list;
    }

}
