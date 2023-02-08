package com.cooper.concurrency.java8;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @reference https://mangkyu.tistory.com/263\
 * @codes https://github.com/MangKyu/java-concurrency/blob/master/src/test/java/com/mangkyu/concurrency/java8/CompletableFutureExceptionTest.java
 */
class CompletableFutureExceptionTest {

    @ParameterizedTest
    @ValueSource(booleans =  {true, false})
    void exceptionally(boolean doThrow) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (doThrow) {
                throw new IllegalArgumentException("Invalid Argument");
            }

            return "Thread: " + Thread.currentThread().getName();
        }).exceptionally(e -> {
            return e.getMessage();
        });

        System.out.println(future.get());
    }

    @ParameterizedTest
    @ValueSource(booleans =  {true, false})
    void handle(boolean doThrow) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (doThrow) {
                throw new IllegalArgumentException("Invalid Argument");
            }

            return "Thread: " + Thread.currentThread().getName();
        }).handle((result, e) -> {
            return e == null
                    ? result
                    : e.getMessage();
        });

        System.out.println(future.get());
    }

}
