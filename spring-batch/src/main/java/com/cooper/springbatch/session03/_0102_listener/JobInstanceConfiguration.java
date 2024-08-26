package com.cooper.springbatch.session03._0102_listener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class JobInstanceConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job BatchJob() {
		return this.jobBuilderFactory.get("Job")
			.start(step03())
			.next(step04())
			.build();
	}

	@Bean
	public Step step03() {
		return stepBuilderFactory.get("step1")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
					JobInstance jobInstance = contribution.getStepExecution().getJobExecution().getJobInstance();
					System.out.println("jobInstance.getId() : " + jobInstance.getId());
					System.out.println("jobInstance.getInstanceId() : " + jobInstance.getInstanceId());
					System.out.println("jobInstance.getJobName() : " + jobInstance.getJobName());
					System.out.println("jobInstance.getJobVersion : " + jobInstance.getVersion());
					return RepeatStatus.FINISHED;
				}
			})
			.build();
	}
	@Bean
	public Step step04() {
		return stepBuilderFactory.get("step2")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("step2 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
