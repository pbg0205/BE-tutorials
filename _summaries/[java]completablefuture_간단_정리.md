## 1. CompletableFuture

 Java8 에 도입된 `java.util.concurrent` 패키지에 존재하는 클래스이다. CompletableFuture 은 Future 의 단점과 한계를 개선한 클래스이다.
 기존 Future 는 `외부에서 완료할 수 없고`, `블로킹(get) 코드를 통해서만 이후 결과를 처리`할 수 있다. 또한 `여러 Future 와 작업을 조합할 수 없고`
 `예외처리를 할 수 없는 단점`이 존재했다.


CompletableFuture 는 이전에 언급한 문제들을 해결했다. CompletableFuture 은 `Completion interface` 를 구현하고 있는데, 이 인터페이스는
작업들을 중첩시키거나 완료 후 콜백을 위해 추가되었다. CompletableFuture 는 `외부에서 작업을 완료`시킬 수 있을 뿐만 아니라 
`콜백 등록` 및 `Future 조합`이 가능하다.

<br>

## 2. CompletableFuture 4가지 기능

CompletableFuture 는 `비동기 작업 실행`, `작업 콜백`, `작업 조합`, `예외 처리` 4가지 기능으로 구분할 수 있다.

`비동기 작업 실행`의 경우, `runAsync` 와 `supplyAsyc` 가 존재한다. **runAsync** 는 **비동기**로 작업을 실행시키며 반환 값이 없어 **Void 타입**
메서드이다. supplyAsync 는 runAsyc 와 동일하게 **비동기**로 작업을 실행시키지만 **반환 값이 존재하는 메서드**이다. 그렇기 때문에 비동기
작업 결과를 받아올 수 있다.

<br>

rynAsync 와 supplyAsync 는 기본적으로 자바7에서 추가된 `ForkJoinPool 의 commonPool()` 을 사용해 작업할 쓰레드를 할당받아 실행한다. 만약 원하는
쓰레드 풀을 사용하고자 한다면 `ExecutorService` 를 파라미터로 전달하면 된다.

```java
// ExecutorService 를 파라미터로 받는 예시
@Test
void supplyAsyncAnotherThreadPool() throws ExecutionException, InterruptedException{
        ExecutorService executorService=Executors.newSingleThreadExecutor();
        CompletableFuture<String> future=CompletableFuture.supplyAsync(()->{
    return"Thread: "+Thread.currentThread().getName();
    },executorService);
}
```

<br>

`작업 콜백`의 경우, `thenApply`, `thenAccept`, `thenRun` 메서드가 있다. `thenApply` 메서드는 Function 을 파라미터로 받아 다른 값으로
반환하는 메서드를 의미한다. `thenAccept` 메서드의 경우 Consumer 를 파라미터로 받아 값을 반환하지 않는 메서드이다. `thenRun` 의 경우 Runnable 을
파라미터로 받는 함수로 반환 값을 받지 않아 다른 작업을 실행하는 함수이다. thenRun 또한 값을 반환하지 않는 메서드이다. (함수형 인터페이스에서 사용하는 메서드와 
같다고 생각하면 편하다.)

```java
@Test
void thenAccept() throws ExecutionException, InterruptedException {
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
        return "Thread: " + Thread.currentThread().getName();
    }).thenAccept(s -> {
        System.out.println(s.toUpperCase());
    });

    future.get();
}
```

<br>

`작업 조합`의 경우, `thenCompose`, `thenCombine`, `allOf`, `anyOf` 메서드가 있다. `thenCompose` 의 경우, 두 작업이 이어서 실행하도록 조합하며 
앞선 결과를 받아서 사용할 수 있다. `thenCombine` 는 작업을 독립적으로 실행되고 얻어진 두 결과를 조합해서 작업을 처리한다. `allOf` 와 `anyOf` 의
차이는 완료된 순서이다. allOf 의 경우, 모든 콜백을 실행하는 메서드이고 anyOf 의 경우 가장 빨리 끝난 작업에 대해서만 콜백이 실행된다.

```java
@Test
void allOf() throws ExecutionException, InterruptedException {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
        return "Hello";
    });

    CompletableFuture<String> cooper = CompletableFuture.supplyAsync(() -> {
        return "cooper";
    });

    List<CompletableFuture<String>> futures = List.of(hello, cooper);

    CompletableFuture<List<String>> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
            .thenApply(v -> futures.stream().
                    map(CompletableFuture::join).
                    collect(Collectors.toList()));

    result.get().forEach(System.out::println); //Hello \n cooper
}
```

<br>

마지막으로 `예외처리` 는 exceptionally, handle(+ handleAsyn) 가 있다. `exceptionally` 의 경우, Function 인터페이스를 파라미터로 받아 발생한
예외를 받아서 예외를 처리하는 함수이다. `handle(+ handleAsync)`의 경우, 결과값, 에러를 모두 받아 예외인 경우와 예외가 아닌 경우를 모두 처리하는 함수이다.
이 함수는 기존 함수들과 다르게 BiFunction 을 파라미터로 받는다.

```java
@ParameterizedTest
@ValueSource(booleans =  {true, false})
void handle(boolean doThrow) throws ExecutionException, InterruptedException {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
        if (doThrow) {
            throw new IllegalArgumentException("Invalid Argument");
        }

        return "Thread: " + Thread.currentThread().getName();
    }).handle((result, exception) -> {
        return (exception == null) ? result : exception.getMessage();
    });

    System.out.println(future.get());
}
```

### Reference

- [[망나니 개발자] CompletableFuture에 대한 이해 및 사용법](https://mangkyu.tistory.com/263)
