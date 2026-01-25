package com.example.employee.manager.employee.staff.service;

import com.example.employee.manager.employee.staff.domain.Meeting;
import com.example.employee.manager.employee.staff.domain.MeetingExample;
import com.example.employee.manager.employee.staff.mapper.MeetingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    public int createMeeting (Meeting request) {
        // 插入数据库
        return meetingMapper.insertSelective(request);
    }

    public List<Meeting> getAllMeeting(String jobNumber) {
        MeetingExample example = new MeetingExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return meetingMapper.selectByExampleWithBLOBs(example);
    }
}

