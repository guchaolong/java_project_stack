package com.zeki.distributedlock.controller;

import com.zeki.distributedlock.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("acquire-lock")
    public String acquireLock() throws Exception {
        boolean acquired = redisService.acquire("test-lock", 100000);
        if (!acquired) {
            return "false";
        }
        Thread.sleep(3000);
        //   redisService.release();

        return "success";
    }
}
