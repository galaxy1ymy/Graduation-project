package com.example.employee.manager.employee.staff.service;

import com.example.employee.manager.employee.staff.domain.GoingOut;
import com.example.employee.manager.employee.staff.domain.GoingOutExample;
import com.example.employee.manager.employee.staff.mapper.GoingOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoingOutService {
    @Autowired
    private GoingOutMapper goingOutMapper;

    public int createGoingOut (GoingOut request) {
        // 插入数据库
        return goingOutMapper.insertSelective(request);
    }

    public List<GoingOut> getAllGoingOut(String jobNumber) {
        GoingOutExample example = new GoingOutExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return goingOutMapper.selectByExampleWithBLOBs(example);
    }


}
