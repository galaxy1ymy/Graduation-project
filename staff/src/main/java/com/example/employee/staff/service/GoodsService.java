package com.example.employee.staff.service;


import com.example.employee.staff.domain.Goods;
import com.example.employee.staff.domain.GoodsExample;
import com.example.employee.staff.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    public int createGoods (Goods request) {
        // 插入数据库
        return goodsMapper.insertSelective(request);
    }

    public List<Goods> getAllGoods(String jobNumber) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }
}
