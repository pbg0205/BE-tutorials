package com.cooper.concurrency.java8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @reference https://mangkyu.tistory.com/263
 * @codes https://github.com/MangKyu/java-concurrency/blob/master/src/test/java/com/mangkyu/concurrency/java8/CompletableFutureComposeTest.java
 */
public class CompletableFutureComposeTest {

    @Test
    void thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });

        // Future 간에 연관 관계가 있는 경우
        CompletableFuture<String> future = hello.thenCompose(this::cooper);
        System.out.println(future.get());
    }

    private CompletableFuture<String> cooper(String message) {
        return CompletableFuture.supplyAsync(() -> {
            return message + " " + "cooper";
        });
    }

    @Test
    void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        });

        CompletableFuture<String> cooper = CompletableFuture.supplyAsync(() -> {
            return "cooper";
        });

        CompletableFuture<String> future = hello.thenCombine(cooper, (h, c) -> h + " " + c);
        System.out.println(future.get());
    }

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

    @Test
    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "Hello";
        });

        CompletableFuture<String> cooper = CompletableFuture.supplyAsync(() -> {
            return "cooper";
        });

        CompletableFuture<Void> future = CompletableFuture.anyOf(hello, cooper).thenAccept(System.out::println);
        future.get(); // cooper
    }
}
