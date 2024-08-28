package com.cooper.springbatch.section07._01flowjob;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
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

@Configuration
@RequiredArgsConstructor
public class FlowJob01Configuration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job batchJob01() {
		return jobBuilderFactory.get("batchJob0701")
			.start(step070101())
			.on(ExitStatus.COMPLETED.getExitCode()).to(step070103())
			.from(step070101())
			.on(ExitStatus.FAILED.getExitCode()).to(step070102())
			.end()
			.build();
	}

	@Bean
	public Step step070101() {
		return stepBuilderFactory.get("step070101")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(final StepContribution stepContribution,
					final ChunkContext chunkContext) throws Exception {
					System.out.println("step01 has executed");
					throw new RuntimeException("step01 has exception");
					// return RepeatStatus.FINISHED; // SUCCESS
				}})
			.build();
	}

	@Bean
	public Step step070102() {
		return stepBuilderFactory.get("step070102")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(final StepContribution stepContribution,
					final ChunkContext chunkContext) throws Exception {
					System.out.println("step02 has executed");
					return RepeatStatus.FINISHED;
				}})
			.build();
	}

	@Bean
	public Step step070103() {
		return stepBuilderFactory.get("step070103")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(final StepContribution stepContribution,
					final ChunkContext chunkContext) throws Exception {
					System.out.println("step03 has executed");
					return RepeatStatus.FINISHED;
				}})
			.build();
	}

}
