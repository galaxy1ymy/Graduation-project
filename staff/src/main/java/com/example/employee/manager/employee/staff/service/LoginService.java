package com.example.employee.manager.employee.staff.service;

import com.example.employee.manager.common.exception.BusinessException;
import com.example.employee.manager.common.exception.BusinessExceptionEnum;
import com.example.employee.manager.employee.staff.domain.Login;
import com.example.employee.manager.employee.staff.mapper.LoginMapper;
import com.example.employee.manager.employee.staff.req.StaffLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;
   /* @Autowired
    private StaffMapper staffMapper;*/

    public Login login(StaffLoginReq  req) {
        Long jobNumber = req.getJobNumber();
        String password = req.getPassword();
        Login login = loginMapper.selectByPrimaryKey(jobNumber);
        if (login == null) {
            throw new BusinessException(BusinessExceptionEnum.STAFF_JOBNUMBER_NULL);
        }
        if (!login.getPassword().equals(password)) {
            throw new BusinessException(BusinessExceptionEnum.STAFF_PASSWORD_ERROR);
        }
        return login;
    }

    /*public String getNameByJobNumber(String jobNumber) {
        StaffExample example = new StaffExample();
        example.createCriteria().andJobNumberEqualTo(jobNumber);

        List<Staff> list = staffMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0).getName();
    }*/

}
