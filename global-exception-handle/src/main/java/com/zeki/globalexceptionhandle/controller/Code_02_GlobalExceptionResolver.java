package com.zeki.globalexceptionhandle.controller;

import com.alibaba.fastjson.JSON;
import com.zeki.common.api.CommonResult;
import com.zeki.globalexceptionhandle.exception.ParamException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Description: 方法2  使用HandlerExceptionResolver   进行全局统一异常处理
 *
 * 在 Spring 源码中，我们可以看出它会获取一个实现了 HandlerExceptionResolver 接口的列表
 * List<HandlerExceptionResolver> resolvers; 如果这个列表不为空，则循环处理其中的异常。
 * HandlerExceptionResolve 虽然能够处理全局异常，但是 Spring 官方不推荐使用它
 *
 * @author GUCHAOLONG
 * @date 2021/4/9 7:37
 */
@Component
public class Code_02_GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     * 异常处理方法
     * 根据方法的返回值：
     * 1、 返回视图 ModeAndView
     * 2、 返回对象 json
     * <p>
     * 如何判断方法的返回值？
     * 通过反射，看方法上面是否申明了@ResponseBody注解
     * 未申明：返回视图
     * 申明了：返回对象
     *
     * @param request  请求
     * @param response 响应
     * @param handler  方法对象
     * @param ex       异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /*
        设置默认异常处理 （返回视图）
         */
        ModelAndView modelAndView = new ModelAndView("error");
        //设置异常信息
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg", "异常信息，请重试...");

        //判断handlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上什么的@ResponseBody注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            //判断responseBody对象是否为空， 空 则返回视图  否则 对象json
            if (responseBody == null) {
                /**
                 * 方法返回视图
                 */
                //判断异常类型
                if (ex instanceof ParamException) {
                    ParamException p = (ParamException) ex;
                    modelAndView.addObject("code", p.getCode());
                    modelAndView.addObject("msg", p.getMessage());
                }
                return modelAndView;
            } else {
                /**
                 * 方法返回数据
                 */
                //设置默认的异常处理
                CommonResult result = CommonResult.failed("系统异常...");
                //判断异常类型，是否是自定义异常
                if (ex instanceof ParamException) {
                    ParamException p = (ParamException) ex;
//                    result.setCode(Long.valueOf(p.getCode()));
                    result.setCode(2345L);
                    result.setMessage(p.getMessage());
                }
                //设置响应类型及编码格式
                response.setContentType("application/json;charset=UTF-8");
                //得到字符输出流
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    String json = JSON.toJSONString(result);
                    out.write(json);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                return null;
            }
        }

        return null;
    }
}
