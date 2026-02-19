package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Business;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.service.BusinessService;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/create")
    public String createBusiness(@RequestBody Business request) {
        Staff staff = staffService.getStaffByJobNumber(request.getJobNumber());
        if (staff == null) {
            return "fail: 找不到员工信息";
        }

        // 附件可选，如果前端没上传就是空字符串或 null
        if (request.getAttachment() == null) {
            request.setAttachment("");
        }

        request.setStatus(0); // 默认待审批
        request.setName(staff.getName());
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());

        int result = businessService.createBusiness(request);
        return result > 0 ? "success" : "fail";
    }


    @GetMapping("/checkAll")
    public List<Business> checkAll(@RequestParam String jobNumber) {
        List<Business> list = businessService.getAllBusiness(jobNumber);
        return list;
    }

    @PutMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestBody Business request) {
        Business business = businessService.getBusinessById(id);
        if (business == null) return "fail: 记录不存在";

        business.setStatus(request.getStatus());
        business.setApproverId(request.getApproverId()); // 可选
        business.setApproveTime(new Date());
        business.setUpdateTime(new Date());

        int result = businessService.updateBusiness(business);
        return result > 0 ? "success" : "fail";
    }

}
