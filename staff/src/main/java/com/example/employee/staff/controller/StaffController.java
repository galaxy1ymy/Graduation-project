package com.example.employee.staff.controller;

import com.example.employee.common.resp.CommonResp;
import com.example.employee.staff.DTO.StaffDTO;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.mapper.StaffMapper;
import com.example.employee.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffMapper staffMapper;

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

    @GetMapping("/list")
    public CommonResp<List<Staff>> list(@RequestParam(required = false) String name) {
        List<Staff> staffs;
        if (name != null && !name.isEmpty()) {
            staffs = staffService.listByName(name);
        } else {
            staffs = staffService.list();
        }
        return new CommonResp<>(staffs);
    }

    @DeleteMapping("/{id}")
    public CommonResp<Boolean> delete(@PathVariable Long id) {
        staffService.deleteById(id);
        return new CommonResp<>(true);
    }

    @PutMapping("/{id}")
    public CommonResp<Boolean> update(@PathVariable Long id, @RequestBody Staff staff) {
        staff.setId(id); // 确保 id 一定存在
        staffService.update(staff);
        return new CommonResp<>(true);
    }

    @PostMapping
    public CommonResp<Boolean> add(@RequestBody Staff staff) {
        staffService.add(staff);
        return new CommonResp<>(true);
    }


    @GetMapping("/by-department")
    public List<StaffDTO> getStaffByDepartment(@RequestParam String department) {
        return staffService.listDTOByDepartment(department);
    }

    @PutMapping("/update-department")
    public CommonResp<Boolean> updateDepartmentName(
            @RequestParam String oldName,
            @RequestParam String newName) {

        staffService.updateDepartmentName(oldName, newName);
        return new CommonResp<>(true);
    }

    @GetMapping("/me")
    public CommonResp<Staff> getMyInfo(
            @RequestHeader("jobNumber") String jobNumber) {

        Staff staff = staffService.getStaffByJobNumber(jobNumber);
        return new CommonResp<>(staff);
    }



}
