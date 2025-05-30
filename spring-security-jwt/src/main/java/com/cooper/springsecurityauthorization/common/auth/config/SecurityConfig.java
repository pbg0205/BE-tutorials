package com.cooper.springsecurityauthorization.common.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

import com.cooper.springsecurityauthorization.domain.member.model.MemberRole;
import com.cooper.springsecurityauthorization.interfaces.auth.handler.error.JwtAccessDeniedHandler;
import com.cooper.springsecurityauthorization.interfaces.auth.handler.error.JwtAuthenticationEntryPoint;
import com.cooper.springsecurityauthorization.interfaces.auth.filter.AccessTokenAuthenticationFilter;
import com.cooper.springsecurityauthorization.interfaces.auth.filter.TokenLoginFilter;
import com.cooper.springsecurityauthorization.interfaces.auth.handler.error.LoginFailureHandler;
import com.cooper.springsecurityauthorization.interfaces.auth.handler.success.LoginSuccessHandler;
import com.cooper.springsecurityauthorization.domain.member.service.JwtAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(sessionManagementConfigurer ->
				sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(authorize -> {
				authorize.requestMatchers("/api/login").permitAll();
				authorize.requestMatchers("/api/members/**").hasAnyRole(MemberRole.USER.name(), MemberRole.ADMIN.name());
				authorize.requestMatchers("/api/admin/**").hasRole(MemberRole.ADMIN.name());
				authorize.anyRequest().authenticated();
			})
			.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter(), TokenLoginFilter.class)
			.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
				httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
				httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler());
				}

			)
		;
		return http.build();
	}

	@Bean
	public AccessDeniedHandler jwtAccessDeniedHandler() {
		return new JwtAccessDeniedHandler();
	}

	@Bean
	public TokenLoginFilter loginFilter() throws Exception {
		final TokenLoginFilter tokenLoginFilter = new TokenLoginFilter("/api/login");

		tokenLoginFilter.setAuthenticationSuccessHandler(jwtLoginAuthenticationSuccessHandler());
		tokenLoginFilter.setAuthenticationFailureHandler(jwtLoginAuthenticationFailureHandler());
		tokenLoginFilter.setAuthenticationManager(authenticationManager());

		return tokenLoginFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new JwtAuthenticationProvider();
	}

	@Bean
	public AuthenticationSuccessHandler jwtLoginAuthenticationSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler jwtLoginAuthenticationFailureHandler() {
		return new LoginFailureHandler();
	}

	@Bean
	public Filter jwtAuthenticationFilter() {
		return new AccessTokenAuthenticationFilter();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
