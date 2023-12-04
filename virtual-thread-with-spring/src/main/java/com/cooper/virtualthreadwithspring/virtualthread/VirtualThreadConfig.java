package com.cooper.virtualthreadwithspring.virtualthread;

import java.util.concurrent.Executors;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

/**
 * 3.2 버전 보다 낮은 버전을 사용 중이라면 가상 스레드 Executor Bean 을 등록
 */
@Configuration
public class VirtualThreadConfig {
	// Web Request 를 처리하는 Tomcat 이 Virtual Thread를 사용하여 유입된 요청을 처리하도록 한다.
	// @Bean
	public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer()
	{
		return protocolHandler -> {
			protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
		};
	}

	// Async Task에 Virtual Thread 사용
	// @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
	public AsyncTaskExecutor asyncTaskExecutor() {
		return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
	}
}
