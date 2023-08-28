package com.example.springcore.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigConfiguration {

    @Bean
    @ConfigurationProperties("mail2")
    public ConfigProperties02 configProperties02() {
        return new ConfigProperties02();
    }

}
