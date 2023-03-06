package hello.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration (boot 사용을 위한 주석처리)
public class HelloConfig {

    @Bean
    public HelloController helloController() {
        return new HelloController();
    }
}
