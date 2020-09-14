package com.zeki.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/14 13:22
 */
@RestController
public class LockTestController {
    @Autowired
    RedisLockUtil redisLockUtil;

    @GetMapping("/lock/{name}")
    public String lock(@PathVariable(value = "name")String name) throws Exception{
        Boolean result = redisLockUtil.tryLock(name, 6000L);
        if(result){
            return "获取锁成功";
        }
        return "获取锁失败";
    }

    @GetMapping("/unlock/{name}")
    public String unlock(@PathVariable(value = "name")String name) throws Exception{
        Boolean result = redisLockUtil.release(name);
        if(result){
            return "释放锁成功";
        }
        return "释放锁失败";
    }
}
