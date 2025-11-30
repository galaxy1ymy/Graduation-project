package com.example.employee.staff.service;

import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.domain.StaffExample;
import com.example.employee.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffMapper staffMapper;

    public int count() {

        return Math.toIntExact(staffMapper.countByExample(null));
    }

    public String getNameByJobNumber(String jobNumber) {
        StaffExample example = new StaffExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);//查询条件

        List<Staff> list = staffMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0).getName();
    }

    // 新增方法：根据工号获取完整 Staff 对象
    public Staff getStaffByJobNumber(String jobNumber) {
        StaffExample example = new StaffExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);

        List<Staff> list = staffMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }





}
