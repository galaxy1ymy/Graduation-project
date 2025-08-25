package com.example.common.controller;

import com.example.common.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ControllerExceptionHandle {
    public static final Logger LOG=LoggerFactory.getLogger(ControllerExceptionHandle.class);

    /**
     * 统一异常处理
     **/
    @ExceptionHandler(value= Exception.class)
    @ResponseBody
    public CommonResp exceptionHandler(Exception e){
        CommonResp commonResp=new CommonResp();
        LOG.error("系统异常：",e);
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getMessage());
        return commonResp;
    }

}
