package com.cooper.springasync.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class CooperMessageConverter {

    private static final String PREFIX = "COOPER";

    @Async("messageThreadPoolExecutor")
    public String convertMessage(String message) {

        if (Objects.equals(message, "exception")) {
            throw new RuntimeException("비동기 예외 발생");
        }

        String convertedMessage = convertedMessage(message);
        log.info("converted Message: {}", convertedMessage);
        return convertedMessage;

    }

    @Async("messageThreadPoolExecutor")
    public CompletableFuture<String> convertMessageWithListenableFuture(String message) {
        String convertedMessage = convertedMessage(message);
        log.info("converted Message: {}", convertedMessage);
        return new AsyncResult<>(convertedMessage).completable();
    }

    private String convertedMessage(String message) {
        return String.format("%s-%s", PREFIX, message);
    }
}
