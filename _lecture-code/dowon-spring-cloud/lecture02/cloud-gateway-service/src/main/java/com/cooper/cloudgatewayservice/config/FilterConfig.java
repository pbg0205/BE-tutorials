package com.cooper.cloudgatewayservice.config;

import com.cooper.cloudgatewayservice.filter.CustomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final CustomFilter customFilter;

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f ->
                                f.addRequestHeader("first-request1", "first-request-header1")
                                        .addResponseHeader("first-response1", "first-response-header1")
                                        .filter(customFilter.apply(new CustomFilter.Config())))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/second-service/**")
                        .filters(f ->
                                f.addRequestHeader("second-request1", "second-request-header1")
                                        .addResponseHeader("second-response1", "second-response-header1")
                                        .filter(customFilter.apply(new CustomFilter.Config()))
                        )
                        .uri("http://localhost:8082"))
                .build();
    }

}

