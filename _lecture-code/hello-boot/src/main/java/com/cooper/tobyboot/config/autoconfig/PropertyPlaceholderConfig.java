package com.cooper.tobyboot.config.autoconfig;

import com.cooper.tobyboot.config.MyAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@MyAutoConfiguration
public class PropertyPlaceholderConfig {

    @Bean
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
