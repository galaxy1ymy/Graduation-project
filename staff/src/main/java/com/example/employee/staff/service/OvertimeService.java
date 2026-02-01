package com.example.employee.staff.service;

import com.example.employee.staff.domain.Overtime;
import com.example.employee.staff.domain.OvertimeExample;
import com.example.employee.staff.mapper.OvertimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OvertimeService {

    @Autowired
    private OvertimeMapper overtimeMapper;

    public int createOvertime(Overtime request) {
        // 插入数据库
        return overtimeMapper.insertSelective(request);
    }

    public List<Overtime> getAllOvertime(String jobNumber) {
        OvertimeExample example = new OvertimeExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return overtimeMapper.selectByExampleWithBLOBs(example);
    }
}
