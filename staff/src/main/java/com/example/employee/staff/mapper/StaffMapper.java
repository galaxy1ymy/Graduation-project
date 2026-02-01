package com.example.employee.staff.mapper;

import com.example.employee.staff.domain.Staff;
import com.example.employee.staff.domain.StaffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaffMapper {
    long countByExample(StaffExample example);

    int deleteByExample(StaffExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Staff row);

    int insertSelective(Staff row);

    List<Staff> selectByExample(StaffExample example);

    Staff selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Staff row, @Param("example") StaffExample example);

    int updateByExample(@Param("row") Staff row, @Param("example") StaffExample example);

    int updateByPrimaryKeySelective(Staff row);

    int updateByPrimaryKey(Staff row);
}