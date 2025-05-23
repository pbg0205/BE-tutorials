# Spring 에서 @Async로 비동기 처리하기 

## @Async ?

`@Async` 어노테이션은 비동기 동작을 지원하는 어노테이션이다. `@Async` 어노테이션을 선언하면 별도의 쓰레드를 할당해 작업을 분리시킨다. @Async 어노테이션을
선언하기 위해서는 `메서드의 접근제한자가 public` 이어야 하고, `자기 호출(self-invocation)` 이면 안됀다. 만약 이 규칙을 지키지 않을 경우에는 @Async
어노테이션이 동작하지 않는다. (이유는 비동기는 `프록시 기반`으로 호출하기 때문에!!)

## @Async & ThreadPool

- 쓰레드 풀은 선언하는 방법은 **application level** 과 **method level** 로 구분된다.

### `application level`

application level 의 경우, `AsyncConfigurer interface` 의 설정 클래스에 `getAsyncExecutor()` 메서드를 재정의해서 구현한다.
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// 출처 : https://www.baeldung.com/spring-async
@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
   @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
```

<br>

### `method level`

method level 을 사용하는 경우는 빈으로 선언하는 방법이다. `@Async` 의 기본적으로 `org.springframework.core.task.SimpleAsyncTaskExecutor`
사용하며 비동기 작업마다 쓰레드를 생성하는 방식으로 동작한다. 하지만 매번 쓰레드를 생성하는 작업은 비용이 크기 때문에 쓰레드풀(ThreadPool) 을 통해 
커스터마이징하는 방식을 사용한다. 쓰레드 풀은 `corePoolSize, maxPoolSize`를 설정할 수 있다. `ThreadPoolTaskExecutor`은 `ThreadPoolExecutor`
를 래핑하고 있는데 setter 를 통해 ThreadPoolExecutor 로 core, max 사이즈를 설정한다. 

`java.util.concurrent.ThreadPoolExecutor` 는 내부에 `LinkedBlockingQueue(BlockingQueue interface)` 를 생성해 관리한다.
기본으로 core size 만큼의 쓰레드를 관리하는데 core size 만큼의 쓰레드가 요청을 처리하고 있다면 BlockingQueue 에 요청을 저장하고 기다린다.
만약 BlockingQueue 에 요청이 가득찰 경우 쓰레드를 추가 생성해서 요청을 처리하며 최대로 생성할 수 있는 쓰레드 수를 maxPoolSize 이다. 만약 스레드 풀의

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "threadPoolExecutor")
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 쓰레드 수
        taskExecutor.setMaxPoolSize(10); // 최대 쓰레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("async-executor-");
        return taskExecutor;
    }

}

```

<br>

종류를 여러 개 설정하고 싶다면, threadPoolTaskExecutor() 같은 빈 생성 메소드를 여러 개 만들고, @Async 설정할 때 원하는 스레드 풀 빈이름을 설정한다.

```java
@Service
@RequiredArgsConstructor
public class MessageService {

    @Async("threadPoolExecutor")
    public void getMessageVoid(String message) {
        System.out.println("this.getClass : " + this.getClass());
        System.out.println("this : " + this);
        System.out.println("current thread name : " + Thread.currentThread().getName());
        System.out.println(message);
    }

    @Async("threadPoolExecutor")
    public CompletableFuture<String> getMessageCompletableFuture(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("this.getClass : " + this.getClass());
            System.out.println("this : " + this);
            System.out.println("current thread name : " + Thread.currentThread().getName());
            return message;
        });
    }

}

```
- [ ] _void method 를 선언할 경우에는 thread pool 설정이 안됀다??_
  ```text
    // void method : customize (X) ?
    this.getClass : class com.cooper.springasync.business.MessageService
    this : com.cooper.springasync.business.MessageService@2b6e1e74
    current thread name : ForkJoinPool.commonPool-worker-19 // prefix 가 설정되지 않는다.
  
    // completable_future method : customize (O)
    this.getClass : class com.cooper.springasync.business.MessageService
    this : com.cooper.springasync.business.MessageService@2b6e1e74
    current thread name : async-executor-2 
  ```

<br>

## exception handling

exception handling 의 경우, Future 하위 타입 계열은 예외 타입을 반환하지만 void method 은 예외를 반환하지 않는다. void method 의 예외 핸들링하기
위해서는 `AsyncUncaughtExceptionHandler interface` 의 추상 메서드인 `handleUncaughtException` 을 재정의하고 빈으로 등록한다.
(@Bean or @Component)

```java
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(
      Throwable throwable, Method method, Object... obj) {
 
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
    
}
```

exception message
```
this.getClass : class com.cooper.springasync.business.MessageService
this : com.cooper.springasync.business.MessageService@1a203b57
current thread name : async-executor-1
2023-02-09 18:03:20.329 ERROR 5839 --- [sync-executor-1] .a.i.SimpleAsyncUncaughtExceptionHandler : Unexpected exception occurred invoking async method: public void com.cooper.springasync.business.MessageService.getMessageException(java.lang.String)
```


### References

- [[baeldung] How To Do @Async in Spring](https://www.baeldung.com/spring-async)
- [[Spring] @Async 사용 방법](https://steady-coding.tistory.com/611)

