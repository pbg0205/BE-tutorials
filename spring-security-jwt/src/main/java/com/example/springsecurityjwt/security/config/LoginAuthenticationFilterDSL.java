package com.example.springsecurityjwt.security.config;

import com.example.springsecurityjwt.security.filter.LoginAuthenticationFilter;
import com.example.springsecurityjwt.security.handler.CustomFailureHandler;
import com.example.springsecurityjwt.security.handler.CustomSuccessHandler;
import com.example.springsecurityjwt.security.jwt.JwtFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class LoginAuthenticationFilterDSL extends AbstractHttpConfigurer<LoginAuthenticationFilterDSL, HttpSecurity> {

    @Autowired
    private JwtFactory jwtFactory;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(authenticationManager);
        loginAuthenticationFilter.setFilterProcessesUrl("/api/login");
        loginAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        loginAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        http.addFilter(loginAuthenticationFilter);
    }

    public static LoginAuthenticationFilterDSL loginAuthenticationFilterDSL() {
        return new LoginAuthenticationFilterDSL();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSuccessHandler(jwtFactory);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomFailureHandler();
    }

}
