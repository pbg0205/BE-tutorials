package com.cooper.springcache;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(ConditionEvaluationReport report) {
        return args -> {
            report.getConditionAndOutcomesBySource().entrySet().stream()
                    .filter(co -> co.getValue().isFullMatch())
                    .filter(co -> co.getKey().indexOf("Jmx") < 0)
                    .forEach(co -> {
                        System.out.println(co.getKey());
                        co.getValue().forEach(c -> System.out.println("\t" + c.getOutcome()));
                    });
        };
    }

}
