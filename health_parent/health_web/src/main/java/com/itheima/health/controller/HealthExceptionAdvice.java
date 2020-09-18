package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author : qiangshengchen
 * @date : 上午 10:50 18/9/2020
 */

@RestControllerAdvice
public class HealthExceptionAdvice {
    /*
    * 自定义异常处理
    * */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException he){
        return new Result(false,he.getMessage());
    }

    /*
    * 所有异常的处理
    * */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        return new Result(false,"系统繁忙，请稍后再试！！！");
    }
}
