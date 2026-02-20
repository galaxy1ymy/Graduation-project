package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Goods;
import com.example.employee.staff.domain.Meeting;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.GoodsService;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public String createGoods(@RequestBody Goods request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }

        // 附件可选，如果前端没上传就是空字符串或 null
        if (request.getAttachment() == null) {
            request.setAttachment("");
        }

        request.setStatus(0); // 默认待审批
        request.setEmployeeName(staff.getName());
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = goodsService.createGoods(request);
        return result > 0 ? "success" : "fail";
    }

    @GetMapping("/checkAll")
    public List<Goods> checkAll(@RequestParam String jobNumber) {
        List<Goods> list = goodsService.getAllGoods(jobNumber);
        return list;
    }

    // 新增审批接口
    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody Meeting request) {
        Goods goods = goodsService.getGoodsById(id);
        if (goods == null) return "fail: 记录不存在";

        goods.setStatus(request.getStatus()); // 1=通过，2=驳回
        goods.setApproverId(request.getApproverId()); // 可选
        goods.setApproveTime(new Date());
        goods.setUpdateTime(new Date());

        int result = goodsService.updateGoods(goods);
        return result > 0 ? "success" : "fail";
    }
}
