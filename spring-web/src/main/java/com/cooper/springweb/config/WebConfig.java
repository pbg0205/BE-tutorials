package com.cooper.springweb.config;

import java.time.Duration;

import javax.servlet.Filter;

import com.cooper.springweb.filter.ExceptionFilter;
import com.cooper.springweb.filter.WhaleUrlPatternFilter;
import com.cooper.springweb.support.ResourceVersion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ResourceVersion version;

    public static final String PREFIX_STATIC_RESOURCES = "/resources";

    /**
     * 만약 URL pattern 을 구분하는 필터를 구현하고 싶은 경우에는 FilterRegistrationBean, setUrlPattern 메서드를 활용하자
     */
    @Bean
    public FilterRegistrationBean<WhaleUrlPatternFilter> uriFilterRegistrationBean() {
        FilterRegistrationBean<WhaleUrlPatternFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new WhaleUrlPatternFilter());
        registrationBean.addUrlPatterns("/api/whale/*");
        registrationBean.setOrder(3);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionRegistrationBean() {
        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ExceptionFilter());
        registrationBean.addUrlPatterns("/api/exception/*");
        registrationBean.setOrder(4);

        return registrationBean;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.addCacheMapping(CacheControl.noCache().cachePrivate(), "/*");
        registry.addInterceptor(interceptor)
            .excludePathPatterns(PREFIX_STATIC_RESOURCES + "/" + version.getVersion() + "/**");
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter etagHeaderFilter = new ShallowEtagHeaderFilter();
        registration.setFilter(etagHeaderFilter);
        registration.addUrlPatterns("/etag", PREFIX_STATIC_RESOURCES + "/" + version.getVersion() + "/*");
        return registration;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl.maxAge(Duration.ofDays(365)).cachePublic();
        registry.addResourceHandler(PREFIX_STATIC_RESOURCES + "/" + version.getVersion() + "/**")
            .addResourceLocations("classpath:/")
            .setCacheControl(cacheControl);
    }

}
