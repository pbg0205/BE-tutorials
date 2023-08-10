package com.cooper.springasync.config;

import com.cooper.springasync.exception.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.setTaskDecorator(new SimpleTaskDecorator());
        /**
         * - 빈으로 선언할 경우, initialize() 를 자동 호출하므로 명시할 필요없음.
         * - initialize() : Set up the ExecutorService.
         */
        // executor.initialize();
        return executor;
    }

    @Bean(name = "messageThreadPoolExecutor")
    public Executor messageThreadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 쓰레드 수
        taskExecutor.setMaxPoolSize(10); // 최대 쓰레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("message-async-task-");
        taskExecutor.setTaskDecorator(new SimpleTaskDecorator());
        return taskExecutor;
    }

    @Bean(name = "githubThreadPoolExecutor")
    public Executor githubThreadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 쓰레드 수
        taskExecutor.setMaxPoolSize(10); // 최대 쓰레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("async-executor-");
        taskExecutor.setTaskDecorator(new SimpleTaskDecorator());
        return taskExecutor;
    }

    @Bean
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

}
