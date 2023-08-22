package com.cooper.cloudgatewayservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cooper.global")
@Getter
@Setter
public class GlobalFilterConfig {

    private String baseMessage;
    private boolean preLogger;
    private boolean postLogger;
    
}
