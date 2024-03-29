package com.cooper.springwebcachecontrol.config;

import java.time.Duration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.cooper.springwebcachecontrol.support.ResourceVersion;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig implements WebMvcConfigurer {

	public static final String PREFIX_STATIC_RESOURCES = "/resources";

	private final ResourceVersion version;

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {

		log.info("resource version: {}", version.getVersion());

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
