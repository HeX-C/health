package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author : qiangshengchen
 * @date : 上午 10:50 18/9/2020
 */

@RestControllerAdvice
public class HealthExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(HealthExceptionAdvice.class);
    /*
    * 自定义异常处理
    * */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException he){
        logger.error("违反业务逻辑",he);
        return new Result(false,he.getMessage());
    }

    /*
    * 所有异常的处理
    * */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        logger.error("发生未知错误",e);
        return new Result(false,"此操作不被允许！！！");
    }
}
