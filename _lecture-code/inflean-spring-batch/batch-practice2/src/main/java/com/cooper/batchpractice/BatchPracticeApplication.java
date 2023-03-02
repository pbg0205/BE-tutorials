package com.cooper.batchpractice2;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BatchPractice2Application {

    public static void main(String[] args) {
        SpringApplication.run(BatchPractice2Application.class, args);
    }

}
