package com.cooper.springredis.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisServer;

import javax.annotation.PostConstruct;

@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("{spring.redis.port}")
    private int redisPort;

    @Value("{spring.redis.host}")
    private String host;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(host, redisPort);
    }

}
