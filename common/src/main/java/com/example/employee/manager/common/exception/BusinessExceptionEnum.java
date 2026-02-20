package com.example.employee.manager.common.exception;

public enum BusinessExceptionEnum {
    STAFF_JOBNUMBER_NULL("工号不存在"),
    STAFF_PASSWORD_ERROR("密码错误"),;

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
