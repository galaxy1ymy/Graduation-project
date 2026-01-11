package com.example.employee.staff.service;

import com.example.employee.staff.domain.LeaveRequest;
import com.example.employee.staff.domain.LeaveRequestExample;
import com.example.employee.staff.mapper.LeaveRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    public int createLeave(LeaveRequest request) {
        // 插入数据库
        return leaveRequestMapper.insertSelective(request);
    }

    public List<LeaveRequest> getAllLeaveRequest(String jobNumber) {
        LeaveRequestExample example = new LeaveRequestExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return leaveRequestMapper.selectByExampleWithBLOBs(example);
    }
}

