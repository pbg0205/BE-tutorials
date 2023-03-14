package com.example.springdataredisexample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        Map<String, RedisCacheConfiguration> expireMap = getExpireMap();
        return RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(Set.of("SAMPLE")) // 해당 역할??
                .withInitialCacheConfigurations(expireMap)
                .build();
    }

    private Map<String, RedisCacheConfiguration> getExpireMap() {
        Map<String, RedisCacheConfiguration> expireMap = new HashMap<>();

        //enum 으로 따로 뽑아서 사용하는 방법도 고려
        expireMap.put("SAMPLE1", getRedisExpiresConfiguration(Duration.ofSeconds(10)));
        expireMap.put("SAMPLE2", getRedisExpiresConfiguration(Duration.ofSeconds(100)));

        return expireMap;
    }

    private RedisCacheConfiguration getRedisExpiresConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(ttl);
    }

}
