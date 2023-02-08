package com.cooper.concurrency.java5;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @ExecutorService 작업(Runnable, Callable) 등록을 위한 인터페이스이다. ExecutorService 는 Executor interface 를 상속받아 작업 등록
 * 뿐만 아니라 실행을 위한 책임도 갖는다. 그래서 일반적으로 쓰레드 풀은 기본적으로 ExecutorService interface 를 구현한다.
 *
 * @ThreadPoolExecutor 대표적인 ExecutorService 구현체이다. ThreadPoolExecutor 는 내부에 있는 BlockingQueue 에 작업들을 등록한다.
 * 적재된 작업은 쓰레드를 할당받아 처리되며, 만약 쓰레드가 없다면 작업을 큐에서 대기하게 된다.
 *
 * @reference https://mangkyu.tistory.com/259
 */
class ExecutorServiceTest {

    @Test
    void shutdown() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());
        executorService.execute(runnable);

        /**
         * @shutdown 새로운 작업들을 더 이상 받아들이지 않음. 작업을 실행할 수 없는 경우, RejectedExecutionException 을 반환함.
         */
        executorService.shutdown();

        RejectedExecutionException result = assertThrows(RejectedExecutionException.class, () -> executorService.execute(runnable));
        assertThat(result).isInstanceOf(RejectedExecutionException.class);
    }

    /**
     * @shutdownNow shutdown 기능 + 이미 제출된 작업들을 인터럽트시킴
     *
     * @isShutdown Executor 의 shutdown 여부 반환
     */
    @Test
    void shutdownNow() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Start");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            System.out.println("End");
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(runnable);

        executorService.shutdownNow();
        Thread.sleep(1000L);
    }

    /**
     * @invokeAll 모든 결과가 나올 ㄸ까지 대기하는 블로킹 방식의 요청이다. 동시에 주어진 작업들이 모두 실행 및 종료되면
     * 작업의 결과물을 List<Future> 형태로 반환한다.
     */
    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            final String result = "Hello";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> coo = () -> {
            Thread.sleep(2000L);
            final String result = "coo";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> per = () -> {
            Thread.sleep(3000L);
            final String result = "per";
            System.out.println("result = " + result);
            return result;
        };

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, coo, per));
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }

        System.out.println("time = " + Duration.between(start, Instant.now()).getSeconds());
        executorService.shutdown();
    }

    /**
     * @invokeAny 가장 빨리 실행된 결과가 나올 떄까지 대기하는 블로킹 방식의 요청이다.
     * 동시 작업들 중에 가장 빨리 완료된 하나의 결과를 Future 로 반환한다.
     */
    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Instant start = Instant.now();

        Callable<String> hello = () -> {
            Thread.sleep(1000L);
            final String result = "Hello";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> mang = () -> {
            Thread.sleep(2000L);
            final String result = "coo";
            System.out.println("result = " + result);
            return result;
        };

        Callable<String> kyu = () -> {
            Thread.sleep(3000L);
            final String result = "per";
            System.out.println("result = " + result);
            return result;
        };

        String result = executorService.invokeAny(Arrays.asList(hello, mang, kyu));
        System.out.println("result = " + result + " time = " + Duration.between(start, Instant.now()).getSeconds());

        executorService.shutdown();
    }

}
