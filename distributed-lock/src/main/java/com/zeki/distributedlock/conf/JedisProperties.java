package com.zeki.distributedlock.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author AA
 * @date 2020/9/16 21:45
 */
@Data
@Component
public class JedisProperties {
    @Value("${redis.server.host}")
    private String host;
    @Value("${redis.server.port}")
    private int port;
    @Value("${redis.server.password}")
    private String password;
    @Value("${redis.server.maxTotal}")
    private int maxTotal;
    @Value("${redis.server.maxIdle}")
    private int maxIdle;
    @Value("${redis.server.minIdle}")
    private int minIdle;
    @Value("${redis.server.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${redis.server.timeOut}")
    private int timeOut;
    @Value("${redis.server.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${redis.server.testOnReturn}")
    private boolean testOnReturn;
    @Value("${redis.server.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${redis.server.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${redis.server.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;
    @Value("${redis.server.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${redis.server.database}")
    private int database;
}
