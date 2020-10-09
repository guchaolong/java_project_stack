package com.zeki.redislua;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Description: RateLimiter 谷歌的guava框架实现的令牌桶算法，单机限流
 *
 * @author AA
 * @date 2020/10/9 22:26
 */
public class TokenBucket {
    public static void main(String[] args) {


        //表示桶的容量为5，且每秒新增5个令牌，速度：200毫秒放1个
        RateLimiter limiter = RateLimiter.create(5);


        //返回值表示从令牌桶中获取一个令牌锁花费的时间，单位为秒
//        System.out.println(limiter.acquire(1));
//        System.out.println(limiter.acquire(1));
//        System.out.println(limiter.acquire(1));



        //突发请求，每秒5个，第一次请求一下子获取50个，理论上要10秒，但我一下子就给你了
        System.out.println(limiter.acquire(50));
        //但是接下来第二个请求过来，获取5个，不能立即给，因为上次给了50个了，我已经虚脱了，要休息10秒的时间，然后再给你吧
        System.out.println(limiter.acquire(5));
        System.out.println(limiter.acquire(50));
        System.out.println(limiter.acquire(5));
        System.out.println(limiter.acquire(50));





    }
}
