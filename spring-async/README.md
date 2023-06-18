# Spring Async

## 설정 과정

`@EnableAsync`

1. @Configuration annotation 이 있는 위치에 `@EnableAsync` 선언하면, 전체 Spring Application Context 에 애노테이션 기반의 <u>**비동기 프로세싱(async processing)**</u> 이 가능하다.
2. `@EnableAsync` 를 선언하면 **<u>기본적으로 Spring 은 연관된 스레드 풀 정의를 검색한다.</u>**
    1. 컨텍스트에 따라 고유한 `TaskExecutor(org.springframework.core.task)` 빈을 찾거나, **taskExecutor** 라는 이름의 `Executor(java.util.concurrent)` 빈을 찾는다.
    2. 둘 중 어느 것도 확인할 수 없는 경우, 비동기 메서드 호출을 처리하기 위해 `SimpleAsyncTaskExecutor(org.springframework.core.task)` 를 사용한다.
3. 커스터마이징하기 위해서는 `AsyncConfigurer` 를 구현해야 한다.
   1. **<u>리턴 타입이 void 인 함수인 경우</u>**, 호출자(caller) 에게 예외를 다시 전송할 수 없다. 기본적으로 잡히지 않은 예외는 **<u>로그에만 기록된다.</u>**
   2. `getAsyncExecutor()` 메서드를 통해 `Executor` 와 `getAsyncUncaughtExceptionHandler()` 를 통해 `AsyncUncaughtExceptionHandler` 를 구현할 수 있다.
   3. `AsyncConfigurer` 클래스는 애플리케이션 컨텍스트 부트스트랩 초기에 초기화된다.
      - 다른 빈에 대한 종속성이 필요한 경우 다른 `PostProcessor` 도 통과할 수 있도록 가능한 **Lazy** 로 선언해야 한다.
    4. 커스터마이징 예시
      ```java    
      @Configuration
      @EnableAsync
      public class AppConfig implements AsyncConfigurer {
   
             @Override
             @Bean // 완전히 관리되는 빈을 원할 경우에는 @Bean 을 선언해야 한다.
             public Executor getAsyncExecutor() {
                 ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
                 executor.setCorePoolSize(7);
                 executor.setMaxPoolSize(42);
                 executor.setQueueCapacity(11);
                 executor.setThreadNamePrefix("MyExecutor-");
                 executor.initialize();
                 return executor;
             }
        
             @Override
             public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                 return new MyAsyncUncaughtExceptionHandler();
             }
      }
      ```

4. 비동기 프로세싱은 프록시 기반(**<u>default: CGLIB</u>**)으로 동작하기 때문에 **<u>자기 호출(self invocation) 을 불가능</u>** 하다.
## 동작 방식

- 
