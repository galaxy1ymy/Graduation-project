package com.example.employee.manager.employee.staff.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StaffLoginReq {

    @NotNull(message = "工号不能为空")
    private Long jobNumber;

    @NotBlank(message = "密码不能为空")
    private String password;

    public Long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Long jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   @Override
    public String toString() {
        return "StaffLoginReq{" +
                "jobNumber=" + jobNumber +
                ", password='" + password + '\'' +
                '}';
    }
}
