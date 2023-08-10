package com.cooper.springcache.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public enum RedisCacheTTL {

    BOOK("book", Duration.ofSeconds(60)),
    PHONE("phone", Duration.ofSeconds(1000));

    private final String cacheName;
    private final Duration ttl;

}
