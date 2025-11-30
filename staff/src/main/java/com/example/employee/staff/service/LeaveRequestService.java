package com.example.employee.staff.service;

import com.example.employee.staff.domain.LeaveRequest;
import com.example.employee.staff.mapper.LeaveRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    public int createLeave(LeaveRequest request) {
        // 插入数据库
        return leaveRequestMapper.insertSelective(request);
    }
}

