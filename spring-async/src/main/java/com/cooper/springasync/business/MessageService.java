package com.cooper.springasync.business;

import com.cooper.springasync.converter.CooperMessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final CooperMessageConverter cooperMessageConverter;

    public String getMessage(String message) {
        log.info("message: {}", message);
        return cooperMessageConverter.convertMessage(message);
    }

    public CompletableFuture<String> getMessageCompletableFuture(String message) {
        log.info("message: {}", message);
        return cooperMessageConverter.convertMessageWithListenableFuture(message);
    }

}
