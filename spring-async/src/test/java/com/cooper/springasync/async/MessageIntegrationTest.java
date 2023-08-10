package com.cooper.springasync.async;

import com.cooper.springasync.business.MessageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageIntegrationTest {

    @Autowired
    private MessageService messageService;

    @Test
    void getMessageVoid() {
        messageService.getMessage("cooper async");
    }

    @Test
    void getMessageCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = messageService.getMessageCompletableFuture("cooper async");
        assertThat(future.get()).isEqualTo("cooper async");
    }

}
