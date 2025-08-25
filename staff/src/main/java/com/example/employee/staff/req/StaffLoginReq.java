package com.example.employee.staff.req;

public class StaffLoginReq {

    private Long jobNumber;
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
