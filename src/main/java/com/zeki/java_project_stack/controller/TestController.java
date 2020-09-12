package com.zeki.java_project_stack.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/12 11:03
 */
@RestController
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String test() {
        return "hello world";
    }

}
