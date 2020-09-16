package com.zeki.distributedlock;

import com.zeki.distributedlock.util.RedisLock;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DistributedLockApplication {

    @Autowired
    private RedisLock redisLock;

    public void test() {
        redisLock.tryLock("order:pay:", 1, () -> {
            // 业务逻辑
        });

        Boolean payResult = redisLock.tryLock("order:pay:", 2, () -> {
            // 业务逻辑
            return true;
        });

        Integer payResult2 = redisLock.tryLock("order:pay:", 2, () -> {
            // 业务逻辑
            return 0;
        });

        String payResult3 = redisLock.tryLock("order:pay:", 2, () -> {
            // 业务逻辑
            return "";
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }

    @Bean
    public Redisson redisson() {
        //此为单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://8.129.45.179:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }


}
