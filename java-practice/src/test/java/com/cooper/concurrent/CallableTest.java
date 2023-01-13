package com.cooper.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Callable Runnable 의 발전된 형태이며, 제네릭을 사용해 결과를 반환할 수 있는 특징이 있다. (Runnable return type : void)
 *
 * @reference https://mangkyu.tistory.com/259
 */
public class CallableTest {

    @Test
    void callable_void() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() {
                final String result = "Thread: " + Thread.currentThread().getName();
                System.out.println(result);
                return null;
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }


    @Test
    void callable_String() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                return "Thread: " + Thread.currentThread().getName();
            }
        };

        executorService.submit(callable);
        executorService.shutdown();
    }

}
