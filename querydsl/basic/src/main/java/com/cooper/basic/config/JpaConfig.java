package com.cooper.basic.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory QueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
