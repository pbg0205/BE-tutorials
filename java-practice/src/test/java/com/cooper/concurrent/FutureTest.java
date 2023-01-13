package com.cooper.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Future Callable interface 의 구현체인 Task 는 쓰레드가 없거나 작업 시간이 오래 걸리는 경우 실행이 미뤄지는 단점이 있었다.
 * 비동기 작업을 통해 미래의 실행 결과를 얻을 수 있는 장점이 있다.
 *
 * @reference https://mangkyu.tistory.com/259
 */
public class FutureTest {

    @Test
    void get() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = callable();

        // It takes 3 seconds by blocking(블로킹에 의해 3초 걸림)
        Future<String> future = executorService.submit(callable);

        System.out.println(future.get());

        executorService.shutdown();
    }

    @Test
    void isCancelled_False() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = callable();

        Future<String> future = executorService.submit(callable);
        assertThat(future.isCancelled()).isFalse();

        executorService.shutdown();
    }

    @Test
    void isCancelled_True() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = callable();

        Future<String> future = executorService.submit(callable);
        future.cancel(true);

        assertThat(future.isCancelled()).isTrue();
        executorService.shutdown();
    }

    @Test
    void isDone_False() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = callable();

        Future<String> future = executorService.submit(callable);

        assertThat(future.isDone()).isFalse();
        executorService.shutdown();
    }

    @Test
    void isDone_True() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = callable();

        Future<String> future = executorService.submit(callable);

        while (future.isDone()) {
            assertThat(future.isDone()).isTrue();
            executorService.shutdown();
        }

    }

    private Callable<String> callable() {
        return new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                Thread.sleep(3000L);
                return "Thread: " + Thread.currentThread().getName();
            }
        };
    }

}
