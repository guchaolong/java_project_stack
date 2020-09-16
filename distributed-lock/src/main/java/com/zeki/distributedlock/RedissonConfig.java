package com.zeki.distributedlock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/15 9:30
 */
@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redisson() {
        //此为单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://8.129.45.179:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

}
