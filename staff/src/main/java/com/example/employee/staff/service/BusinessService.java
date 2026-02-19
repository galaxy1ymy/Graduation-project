package com.example.employee.staff.service;

import com.example.employee.staff.domain.Business;
import com.example.employee.staff.domain.BusinessExample;
import com.example.employee.staff.mapper.BusinessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    public int createBusiness(Business request) {
        // 插入数据库
        return businessMapper.insertSelective(request);
    }

    public List<Business> getAllBusiness(String jobNumber) {
        BusinessExample example = new BusinessExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return businessMapper.selectByExampleWithBLOBs(example);
    }

    public Business getBusinessById(Long id) {
        return businessMapper.selectByPrimaryKey(id);
    }

    public int updateBusiness(Business business) {
        return businessMapper.updateByPrimaryKeySelective(business);
    }

}
