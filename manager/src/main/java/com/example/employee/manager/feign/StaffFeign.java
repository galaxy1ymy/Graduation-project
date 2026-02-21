package com.example.employee.manager.feign;

import com.example.employee.manager.DTO.StaffDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "staff", url = "http://localhost:8001") // staff 服务地址
public interface StaffFeign {

    @GetMapping("/staff/people/by-department")
    List<StaffDTO> getStaffByDepartment(@RequestParam("department") String department);
}
