package com.zeki.java_project_stack.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/14 16:43
 */
@Component
public class RedisUti {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    redisTemplate.opsForValue();　　//操作字符串
//            redisTemplate.opsForHash();　　 //操作hash
//            redisTemplate.opsForList();　　 //操作list
//            redisTemplate.opsForSet();　　  //操作set
//            redisTemplate.opsForZSet();　 　//操作有序set
//
//


}
