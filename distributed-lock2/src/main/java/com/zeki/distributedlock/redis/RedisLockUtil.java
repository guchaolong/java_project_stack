package com.zeki.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/14 12:51
 */
@Component
public class RedisLockUtil {

    //定义默认超时时间 10秒
    private static final Integer LOCK_TIME_OUT = 10000;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 外部调用加锁方法
     *
     * @param key     锁的名字
     * @param timeout 尝试多长时间后放弃
     * @return
     */
    public boolean tryLock(String key, Long timeout) throws Exception {

        Long startTime = System.currentTimeMillis();

        boolean flag = false;

        //死循环获取锁，1 成功推出 2 超时退出
        while (true) {
            //判断是否超时
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                break;
            } else {
                //获取锁
                flag = lock(key);
                if (flag) {
                    break;
                } else {
                    Thread.sleep(100);
                }
            }
        }
        return flag;
    }

    public Boolean lock(String key) {
        return (Boolean) stringRedisTemplate.execute((RedisCallback) redisConnection -> {
            Long time = System.currentTimeMillis();

            //设置锁超时时间。getset延期时间
            Long timeout = time + LOCK_TIME_OUT + 1;
            System.out.println(" value lock  : " + timeout);

            //setNX加锁 并获取加锁结果 Jedis.setnx(String, String)  redisConnection.setnx(bytes, bytes)
            Boolean result = redisConnection.setNX(key.getBytes(), String.valueOf(timeout).getBytes());

            if (result) {
                System.out.println(" ok");
                return true;
            }

            //加锁失败，判断是否超时
            if (checkLock(key)) {
                //设置成功后会返回旧的有效时间
                byte[] newTime = redisConnection.getSet(key.getBytes(), String.valueOf(timeout).getBytes());
                System.out.println("原value已经小于当前时间，要重新设置时间" + "原value： " + new String(newTime) + "新value" + timeout);
                if (time > Long.valueOf(new String(newTime))) {
                    System.out.println(" time > newTime ");
                    return true;
                }
            }
            //默认加锁失败
            return false;
        });
    }

    private boolean checkLock(String key) {
        return (Boolean) stringRedisTemplate.execute((RedisCallback) redisConnection -> {
            //获取锁的超时时间
            byte[] bytes = redisConnection.get(key.getBytes());
            System.out.println("当前value : " + new String(bytes));
            try {
                //判断锁的有效时间是否大于当前时间

                long curr = System.currentTimeMillis();
                Long timeout = Long.valueOf(new String(bytes));
                if (curr > timeout) {
                    System.out.println("当前时间大于设置的过期时间：" + curr + " > " + timeout);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
        });
    }

    public Boolean release(String key) {
        return (Boolean) stringRedisTemplate.execute((RedisCallback) redisConnection -> {
            Long del = redisConnection.del(key.getBytes());
            if (del > 0) {
                return true;
            }
            return false;
        });
    }

}
