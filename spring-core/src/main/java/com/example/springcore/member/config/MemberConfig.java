package com.example.springcore.member.config;

import com.example.springcore.member.beans.MemberBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MemberBean memberBean() {
        return new MemberBean();
    }

}
