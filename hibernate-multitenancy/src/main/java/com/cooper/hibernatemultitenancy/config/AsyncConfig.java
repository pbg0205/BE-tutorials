package com.cooper.hibernatemultitenancy.config;

import com.cooper.hibernatemultitenancy.config.async.SimpleAsyncUncaughtExceptionHandler;
import com.cooper.hibernatemultitenancy.config.async.TenantAwareTaskDecorator;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    public TaskDecorator TenantAwareTaskDecorator() {
        return new TenantAwareTaskDecorator();
    }

    @Bean
    public AsyncUncaughtExceptionHandler SimpleAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
