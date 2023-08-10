package com.cooper.springasync.controller;

import com.cooper.springasync.business.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/api/messages/async/simple")
    public ResponseEntity<String> sendMessageSync(String message) {
        log.info("request message: {}", message);
        return ResponseEntity.ok(messageService.getMessage(message));
    }

    @GetMapping("/api/messages/async/completable-future")
    public ResponseEntity<String> sendMessageAsync(String message) throws ExecutionException, InterruptedException, TimeoutException {
        log.info("request message: {}", message);
        CompletableFuture<String> messageCompletableFuture = messageService.getMessageCompletableFuture(message);
        messageCompletableFuture.thenApply((receivedMessage) -> {
            log.info("response message: {}", receivedMessage);
            return receivedMessage;
        });

        return ResponseEntity.ok(messageCompletableFuture.get());
    }

}
