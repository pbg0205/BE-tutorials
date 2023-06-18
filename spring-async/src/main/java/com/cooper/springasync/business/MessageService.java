package com.cooper.springasync.business;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Async("messageThreadPoolExecutor")
    public void getMessageVoid(String message) {
        System.out.println("this.getClass : " + this.getClass());
        System.out.println("this : " + this);
        System.out.println("current thread name : " + Thread.currentThread().getName());
        System.out.println(message);
    }

    @Async("messageThreadPoolExecutor")
    public void getMessageException(String message) {
        System.out.println("this.getClass : " + this.getClass());
        System.out.println("this : " + this);
        System.out.println("current thread name : " + Thread.currentThread().getName());
        throw new RuntimeException("비동기 예외 발생");
    }

    @Async("messageThreadPoolExecutor")
    public CompletableFuture<String> getMessageCompletableFuture(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("this.getClass : " + this.getClass());
            System.out.println("this : " + this);
            System.out.println("current thread name : " + Thread.currentThread().getName());
            return message;
        });
    }

}
