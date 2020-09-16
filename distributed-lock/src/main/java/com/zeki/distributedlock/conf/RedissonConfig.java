package com.zeki.distributedlock.conf;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/16 21:57
 */
@Configuration
public class RedissonConfig {
    @Resource
    private JedisProperties prop;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + prop.getHost() + ":" + prop.getPort())
                .setPassword(prop.getPassword()).setDatabase(prop.getDatabase());
        return Redisson.create(config);

    }
}
