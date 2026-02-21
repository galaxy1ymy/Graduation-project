package com.example.employee.manager.service;

import com.example.employee.manager.DTO.StaffDTO;
import com.example.employee.manager.domain.Department;
import com.example.employee.manager.feign.StaffFeign;
import com.example.employee.manager.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private StaffFeign staffFeign;

    // 查询所有部门
    public List<Department> listAll() {
        return departmentMapper.selectByExample(null); // null 表示不带条件，查询所有
    }

    // 根据ID查询部门
    public Department getById(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    // 创建部门
    public void create(Department department) {
        departmentMapper.insertSelective(department); // insertSelective 不会覆盖 null 字段
    }

    // 更新部门
    public void update(Department department) {
        departmentMapper.updateByPrimaryKeySelective(department); // 只更新非空字段
    }

    // 删除部门
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    public List<StaffDTO> getStaffByDepartment(String department) {
        List<StaffDTO> list = staffFeign.getStaffByDepartment(department);
        return list.stream().map(dto -> {
            StaffDTO newDto = new StaffDTO();
            newDto.setId(dto.getId());
            newDto.setJobNumber(dto.getJobNumber());
            newDto.setName(dto.getName());
            newDto.setDepartment(dto.getDepartment());
            newDto.setPosition(dto.getPosition());
            newDto.setGender(dto.getGender());
            newDto.setAge(dto.getAge());
            newDto.setPhone(dto.getPhone());
            return newDto;
        }).toList();
    }

}