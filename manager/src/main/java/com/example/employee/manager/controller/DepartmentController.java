package com.example.employee.manager.controller;

import com.example.employee.manager.DTO.StaffDTO;
import com.example.employee.manager.domain.Department;
import com.example.employee.manager.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments") // 管理员接口前缀
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // 查询所有部门
    @GetMapping
    public List<Department> listAll() {
        return departmentService.listAll();
    }

    // 根据ID查询部门
    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    // 创建部门
    @PostMapping
    public void create(@RequestBody Department department) {
        departmentService.create(department);
    }

    // 更新部门
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Department department) {
        department.setId(id);
        departmentService.update(department);
    }

    // 删除部门
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }

    @GetMapping("/{id}/staff")
    public List<StaffDTO> getStaffByDepartment(@PathVariable Long id) {
        Department dept = departmentService.getById(id);
        if (dept == null) {
            return List.of();
        }
        return departmentService.getStaffByDepartment(dept.getName());
    }



}
