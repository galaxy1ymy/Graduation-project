package com.example.employee.staff.controller;

import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.manager.common.resp.CommonResp;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/count")
    public Integer count() {
        return staffService.count();
    }

    @GetMapping("/active/count")
    public Integer activeCount() {
        return staffService.countByStatus("1");
    }


    @GetMapping("/getNames")
    public String getNameByJobNumber(@RequestParam String jobNumber) {  //绑定 HTTP 请求参数 到方法参数的注解
        return staffService.getNameByJobNumber(jobNumber);
    }


    /**
     * 员工列表
     */
    @GetMapping("/list")
    public CommonResp<List<Staff>> list() {
        return new CommonResp<>(staffService.list());
    }

    @DeleteMapping("/{id}")
    public CommonResp<Boolean> delete(@PathVariable Long id) {
        staffService.deleteById(id);
        return new CommonResp<>(true);
    }

    @PutMapping
    public CommonResp<Boolean> update(@RequestBody Staff staff) {
        staffService.update(staff);
        return new CommonResp<>(true);
    }





}
