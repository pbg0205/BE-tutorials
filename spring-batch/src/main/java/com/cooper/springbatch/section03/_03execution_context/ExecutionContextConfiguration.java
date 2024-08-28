package com.cooper.springbatch.section03._03execution_context;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	private final ExecutionContextTasklet1 executionContextTasklet1;
	private final ExecutionContextTasklet2 executionContextTasklet2;
	private final ExecutionContextTasklet3 executionContextTasklet3;
	private final ExecutionContextTasklet4 executionContextTasklet4;

	@Bean
	public Job BatchJob11() {
		return this.jobBuilderFactory.get("job21")
			.start(step11())
			.next(step12())
			.next(step13())
			.next(step14())
			.build();
	}

	@Bean
	public Step step11() {
		return stepBuilderFactory.get("step11")
			.tasklet(executionContextTasklet1)
			.build();
	}
	@Bean
	public Step step12() {
		return stepBuilderFactory.get("step12")
			.tasklet(executionContextTasklet2)
			.build();
	}
	@Bean
	public Step step13() {
		return stepBuilderFactory.get("step13")
			.tasklet(executionContextTasklet3)
			.build();
	}
	@Bean
	public Step step14() {
		return stepBuilderFactory.get("step14")
			.tasklet(executionContextTasklet4)
			.build();
	}
}
