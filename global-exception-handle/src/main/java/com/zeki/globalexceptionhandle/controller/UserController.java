package com.zeki.globalexceptionhandle.controller;

import com.zeki.globalexceptionhandle.exception.ParamException;
import com.zeki.globalexceptionhandle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author GUCHAOLONG
 * @date 2021/4/9 5:51
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return userService.biz();
    }

}
