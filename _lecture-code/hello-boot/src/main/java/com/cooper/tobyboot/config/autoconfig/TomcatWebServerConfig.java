package com.cooper.tobyboot.config.autoconfig;

import com.cooper.tobyboot.config.ConditionalMyOnClass;
import com.cooper.tobyboot.config.EnableMyConfigurationProperties;
import com.cooper.tobyboot.config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@MyAutoConfiguration
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
@EnableMyConfigurationProperties(ServerProperties.class) // spring style: @Import(ServerProperties.class)
public class TomcatWebServerConfig {

    private final ServerProperties serverProperties;

    public TomcatWebServerConfig(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setContextPath(serverProperties.getContextPath());
        tomcatServletWebServerFactory.setPort(serverProperties.getPort());
        return tomcatServletWebServerFactory;
    }

}
