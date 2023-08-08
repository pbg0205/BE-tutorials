package com.cooper.hibernatemultitenancy.config;

import com.cooper.hibernatemultitenancy.config.async.TenantAwareTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public TaskDecorator TenantAwareTaskDecorator() {
        return new TenantAwareTaskDecorator();
    }

}
