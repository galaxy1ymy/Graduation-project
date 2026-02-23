package com.example.employee.staff.service;

import com.example.employee.staff.DTO.StaffDTO;
import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.domain.StaffExample;
import com.example.employee.staff.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffMapper staffMapper;

    public int count() {

        return Math.toIntExact(staffMapper.countByExample(null));
    }

    public String getNameByJobNumber(String jobNumber) {
        StaffExample example = new StaffExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);//查询条件

        List<Staff> list = staffMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0).getName();
    }

    // 新增方法：根据工号获取完整 Staff 对象
    public Staff getStaffByJobNumber(String jobNumber) {
        StaffExample example = new StaffExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);

        List<Staff> list = staffMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    public int countByStatus(String status) {
        StaffExample example = new StaffExample();
        example.createCriteria().andStatusEqualTo(status);
        return Math.toIntExact(staffMapper.countByExample(example));
    }


    /**
     * 员工列表
     */
    public List<Staff> list() {
        StaffExample example = new StaffExample();
        example.setOrderByClause("id desc");
        return staffMapper.selectByExample(example);
    }


    public void deleteById(Long id) {
        staffMapper.deleteByPrimaryKey(id);
    }

    public void update(Staff staff) {
        staffMapper.updateByPrimaryKeySelective(staff);
    }

    public void add(Staff staff) {
        staffMapper.insertSelective(staff);
    }

    public List<Staff> listByName(String name) {
        StaffExample example = new StaffExample();
        example.createCriteria().andNameLike("%" + name + "%"); // 模糊查询
        example.setOrderByClause("id desc");
        return staffMapper.selectByExample(example);
    }

    public List<Staff> listByDepartment(String department) {
        StaffExample example = new StaffExample();
        example.createCriteria()
                .andDepartmentEqualTo(department)
                .andStatusEqualTo("1"); // 只查在职员工
        example.setOrderByClause("id desc");
        return staffMapper.selectByExample(example);
    }

    public List<StaffDTO> listDTOByDepartment(String department) {
        List<Staff> staffList = listByDepartment(department);
        return staffList.stream().map(staff -> {
            StaffDTO dto = new StaffDTO();
            dto.setId(staff.getId());
            dto.setJobNumber(staff.getJobNumber());
            dto.setName(staff.getName());
            dto.setGender(staff.getGender());
            dto.setAge(staff.getAge());
            dto.setPhone(staff.getPhone());
            dto.setPosition(staff.getPosition());
            dto.setDepartment(staff.getDepartment());
            return dto;
        }).toList();
    }

    public void updateDepartmentName(String oldName, String newName) {
        StaffExample example = new StaffExample();
        example.createCriteria().andDepartmentEqualTo(oldName);

        Staff staff = new Staff();
        staff.setDepartment(newName);

        staffMapper.updateByExampleSelective(staff, example);
    }







}
