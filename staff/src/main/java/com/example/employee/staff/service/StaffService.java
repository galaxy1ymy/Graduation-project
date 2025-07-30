package com.example.employee.staff.service;

import com.example.employee.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    private StaffMapper staffMapper;

    public int count() {

        return Math.toIntExact(staffMapper.countByExample(null));
    }

}
