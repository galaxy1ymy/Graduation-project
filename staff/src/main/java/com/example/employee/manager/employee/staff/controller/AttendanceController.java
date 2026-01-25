package com.example.employee.manager.employee.staff.controller;

import com.example.employee.manager.employee.staff.domain.AttendanceRecords;
import com.example.employee.manager.employee.staff.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @PostMapping("/punch")
    public Map<String,Object> punch(@RequestParam String jobNumber, @RequestParam String type) {
        Map<String, Object> result = new HashMap<>();
        try {
            attendanceService.punch(jobNumber, type);
            result.put("success", true);
            result.put("message", type + " 打卡成功");
            result.put("time", sdf.format(new Date())); // 返回当前打卡时间
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "打卡失败");
        }
        return result;
    }

    @GetMapping("/status")
    public Map<String,Object> status(@RequestParam String jobNumber) {
        boolean punchedUp = attendanceService.hasPunchedToday(jobNumber, "上班");
        boolean punchedDown = attendanceService.hasPunchedToday(jobNumber, "下班");
        return Map.of("punchedUp", punchedUp, "punchedDown", punchedDown);
    }

    // 查询打卡记录列表
    @GetMapping("/list")
    public List<AttendanceRecords> list(@RequestParam String jobNumber) {
        return attendanceService.getRecords(jobNumber);
    }
}
