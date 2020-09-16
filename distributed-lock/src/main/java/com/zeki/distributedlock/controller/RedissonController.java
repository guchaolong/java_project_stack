package com.zeki.distributedlock.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/15 9:37
 */
@RestController
public class RedissonController {

    @Autowired
    Redisson redisson;


    @GetMapping
    public void redissonTest() {

        String lockKey = "product_oo1";
        //1 拿到锁对象
        RLock redissonLock = redisson.getLock(lockKey);
        try {
            // 2 加锁
            redissonLock.lock(30, TimeUnit.SECONDS);
            //
            System.out.println("业务逻辑");
        } finally {
            //3 释放锁
            redissonLock.unlock();
        }
    }
}
