package com.zeki.globalexceptionhandle.controller;

import com.zeki.common.api.CommonResult;
import com.zeki.common.exception.BusinessException;
import com.zeki.globalexceptionhandle.exception.ParamException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ,;,,;
 * ,;;'(    社
 * __      ,;;' ' \   会
 * /'  '\'~~'~' \ /'\.)  主
 * ,;(      )    /  |.     义
 * ,;' \    /-.,,(   ) \    码
 * ) /       ) / )|    农
 * ||        ||  \)
 * (_\       (_\
 *
 *
 * 问题：
 * 项目中都会有异常，还有些自定义异常，controller、dao、service层都会出现异常，
 * 1、如果没有处理异常，就会返回给客户端一些异常栈信息，影响用户体验
 * 2、如果各自在代码中处理异常，就会出现很多的try catch代码，大量重复代码，高耦合，后期难以维护
 *
 * 所以需要统一异常处理
 * 也不会返回给客户端敏感信息，各层也不用再try catch了，减少了很大工作量
 *
 * @description: 方法1：@ControllerAdvice + @ExceptionHandler
 * @ControllerAdvice 一般是和 @ExceptionHandler 组合在一起使用的。官方也推荐用这种方式处理统一全局异常
 * 全局异常处理，避免没处理的异常敏感信息直接暴露给客户端,在页面上打印一些栈信息
 **/
@ControllerAdvice
public class Code_01_RequestBadExceptionHandler {

    /**
     * 兜底异常捕捉
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResult exceptionHandler(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
        }
        return CommonResult.failed("请求错误:->" + e.getMessage());
    }

    //可自定义异常，执行捕捉

    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResult paramExceptionHandler(ParamException e) {
        if (e instanceof ParamException) {
            ParamException paramException = (ParamException) e;
        }
        return CommonResult.failed("请求错误：->" + e.getMessage());
    }

}
