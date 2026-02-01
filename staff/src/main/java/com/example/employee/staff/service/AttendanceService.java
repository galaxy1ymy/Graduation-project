package com.example.employee.staff.service;

import com.example.employee.staff.domain.AttendanceRecords;
import com.example.employee.staff.domain.AttendanceRecordsExample;
import com.example.employee.staff.mapper.AttendanceRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRecordsMapper attendanceRecordsMapper;

    // 判断今天是否已打卡
    public boolean hasPunchedToday(String jobNumber, String type) {
        AttendanceRecordsExample example = new AttendanceRecordsExample();

        Calendar calendar = Calendar.getInstance();

        // 今天开始
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // 今天结束
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        example.createCriteria()
                .andJobNumberEqualTo(jobNumber)
                .andTypeEqualTo(type)
                .andPunchTimeBetween(startOfDay, endOfDay);

        return attendanceRecordsMapper.countByExample(example) > 0;
    }

    // 打卡
    public void punch(String jobNumber, String type) {
        if(hasPunchedToday(jobNumber, type)) {
            throw new RuntimeException(type + " 已打卡");
        }
        AttendanceRecords record = new AttendanceRecords();
        record.setJobNumber(jobNumber);
        record.setPunchTime(new Date());
        record.setType(type);
        record.setCreatedAt(new Date());
        record.setUpdatedAt(new Date());
        attendanceRecordsMapper.insertSelective(record);
    }

    // 查询打卡记录列表
    public List<AttendanceRecords> getRecords(String jobNumber) {
        AttendanceRecordsExample example = new AttendanceRecordsExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return attendanceRecordsMapper.selectByExample(example);
    }

    // 今日出勤人数（去重）
    public int todayCount() {
        AttendanceRecordsExample example = new AttendanceRecordsExample();

        Calendar calendar = Calendar.getInstance();

        // 今天开始 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // 今天结束 23:59:59.999
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        example.createCriteria()
                .andPunchTimeBetween(startOfDay, endOfDay);

        // 只查 jobNumber，减少数据量
        example.setDistinct(true);

        List<AttendanceRecords> list =
                attendanceRecordsMapper.selectByExample(example);

        // Java 层去重
        return (int) list.stream()
                .map(AttendanceRecords::getJobNumber)
                .distinct()
                .count();
    }



}
