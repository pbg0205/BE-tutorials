package com.example.springcore.aop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AopRunner implements CommandLineRunner {

    private final SampleService sampleService;

    @Override
    public void run(String... args) throws Exception {
        sampleService.serve("haha");
    }

}
