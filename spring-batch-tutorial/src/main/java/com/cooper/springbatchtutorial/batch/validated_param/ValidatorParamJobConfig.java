package com.cooper.springbatchtutorial.batch.validated_param;

import com.cooper.springbatchtutorial.batch.validated_param.validator.FileParamValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * desc: 파일 이름 파라미터 전달 그리고 검증
 * <p>
 * run: --spring.batch.job.names=validatedParamJob -fileName=test.csv
 */
@Configuration
@RequiredArgsConstructor
public class ValidatorParamJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job validatedParamJob(Step validatedParamStep) {
        return jobBuilderFactory.get("validatedParamJob")
                // .validator(new FileParamValidator())
                .validator(multiValidator())
                .incrementer(new RunIdIncrementer())
                .start(validatedParamStep)
                .build();
    }

    /**
     * desc: 여러 validation 을 하고 싶은 경우에는 CompositeJobParametersValidator 을 사용한다.
     */
    @Bean
    public CompositeJobParametersValidator multiValidator() {
        CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
        validator.setValidators(List.of(new FileParamValidator()));
        return validator;
    }

    @JobScope
    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet) {
        return stepBuilderFactory.get("validatedParamStep")
                .tasklet(validatedParamTasklet)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return (contribution, chunkContext) -> {
            System.out.println("fileName = " + fileName);
            System.out.println("validated param tasklet");
            return RepeatStatus.FINISHED;
        };
    }

}
