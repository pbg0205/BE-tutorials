# @TransactionalEventListener 사용기

## 1. @TransactionalEventListener 을 통해 객체를 관리하고 싶은 경우

1. `@Transactional + @Async`
2. `@Transactional(propagation = Propagation.REQUIRED_NEW) (sync)`
   - 동기 동작에서는 로직이 완료될 때까지 connection 을 놓지 않기 때문에 좋은 방법은 아니다.  
3. `@Transactional -> 비정상 동작`

<br>

## 2. `@Transactional + @Async` 사용 예시

### [1] `@Transactional + @Async`

### (1) Spring 비동기 지원 처리

```java
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }

}

```

1. 별도의 커스터 마이징이 없을 경우, `SimpleAsyncTaskExecutor` 를 사용하는데, 매번 비동기 로직을 호출할 때마다 Thread 를 생성하므로
   자원의 비용 소모가 크다.
2. 이를 방지하기 위해 ThreadPool 을 활용할 수 있도록  커스터마이징 하자. `@EnableAsync` 를 선언하면 기본적으로 Spring 은 연관된 스레드 풀 정의를 
   검색한다.
    - `TaskExecutor(org.springframework.core.task)` 타입의 빈
    - `Executor(java.util.concurrent)` 타입의 빈

<br>

### (2) @TransactionalEventListener 컴포넌트 작성

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class BookTxListener {

    private final BookHistoryRepository bookHistoryRepository;

    @Async
    @TransactionalEventListener
    @Transactional
    public void commitSuccessLog(Book book) {
        BookHistory bookHistory = new BookHistory(BookTxHistoryType.COMMIT, book.getIsbn());
        bookHistoryRepository.save(bookHistory);
        log.info("[[Current Thread: {}]][commitSuccessLog] - book history: {}", Thread.currentThread(), bookHistory);
    }

}
```
- 호출할 `@TransactionEventListener` 로직에 `@Async` 어노테이션을 할당하면 **commitSuccessLog** 메서드는 별도의 쓰레드를 할당하여 비동기로 동작한다.
- 해당 메서드는 트랜잭션 커밋 이후 시점에 `Book` 이라는 이벤트를 구독하는 역할을 한다.

<br>

### (3) 이벤트 발행하기

```java
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Book createBook(String bookName, String isbn) {
        log.info("[createBook] bookName: {}, isbn: {}", bookName, isbn);
        Book book = bookRepository.save(new Book(bookName, isbn));
        log.info("[[Current Thread: {}]][createBook] - book: {}", Thread.currentThread(), book);
        applicationEventPublisher.publishEvent(book);
        return book;
    }

}

```
1. ApplicationEventPublisher
    - 스프링 컨텍스트에서 제공하는 이벤트 발행을 캡슐화한 인터페이스
    - `publishEvent(Event event)` 를 사용하면 해당 메세지를 구독하는 컴포넌트가 전달받아 사용한다.
    - 하지만, 단일 인스턴스 환경에서만 동작하므로 해당 내용 주의!

## 3. @Tansactional + sync 를 비정상 동작하는 이유

> Book 을 추가하는데 Transaction 이 열리고 동기 처리하고 나서 <u>Transaction 이 완료 및 Session 이 닫혔는데</u> BookHistory 를 추가하는 부분에서
> <u>이미 종료된 기존의 Transaction 을 찾으려고 해서 에러</u>

```
2023-07-08 01:03:47.666 DEBUG 3849 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [com.cooper.baseentityeventlistener.book.application.BookService.createBook]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
...
2023-07-08 01:03:47.685 DEBUG 3849 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
...
2023-07-08 01:03:47.783 DEBUG 3849 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
...

2023-07-08 01:03:47.788 DEBUG 3849 --- [    Test worker] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
```
