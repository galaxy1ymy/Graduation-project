package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Meeting;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.MeetingService;
import com.example.employee.staff.service.StaffService;
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
        request.setEmployeeName(staff.getName());
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

    // 新增审批接口
    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody Meeting request) {
        Meeting meeting = meetingService.getMeetingById(id);
        if (meeting == null) return "fail: 记录不存在";

        meeting.setStatus(request.getStatus()); // 1=通过，2=驳回
        meeting.setApproverId(request.getApproverId()); // 可选
        meeting.setApproveTime(new Date());
        meeting.setUpdateTime(new Date());

        int result = meetingService.updateMeeting(meeting);
        return result > 0 ? "success" : "fail";
    }

}
