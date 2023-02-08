package com.cooper.concurrency.java8;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @reference https://mangkyu.tistory.com/263
 * @codes https://github.com/MangKyu/java-concurrency/blob/master/src/test/java/com/mangkyu/concurrency/java8/CompletableFutureCallbackTest.java
 */
class CompletableFutureCallbackTest {

    @Test
    void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }).thenApply(s -> {
            return s.toUpperCase();
        });

        System.out.println(future.get());
    }

    @Test
    void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }).thenAccept(s -> {
            System.out.println(s.toUpperCase());
        });

        future.get();
    }


    @Test
    void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        }).thenRun(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
        });

        future.get();
    }

}
