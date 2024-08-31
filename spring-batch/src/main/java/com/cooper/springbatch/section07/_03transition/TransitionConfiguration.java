package com.cooper.springbatch.section07._03transition;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class TransitionConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job batchJob() {
		return this.jobBuilderFactory.get("batchJob0703")
			.start(step070301())
			.on("FAILED")
			.to(step070302())
			.on("*")
			.stop()
			.from(step070301()).on("*")
			.to(step070305())
			.next(step070306())
			.on("COMPLETED")
			.end()
			.end()
			.build();
	}

	@Bean
	public Flow flow070301() {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
		flowBuilder
			.start(step070303())
			.next(step070304())
			.end();
		return flowBuilder.build();
	}

	@Bean
	public Step step070301() {
		return stepBuilderFactory.get("step1")
			.tasklet((contribution, chunkContext) -> {
				System.out.println(">> step1 has executed");
				//                    contribution.setExitStatus(ExitStatus.FAILED);
				return RepeatStatus.FINISHED;
			})
			.build();
	}
	@Bean
	public Step step070302() {
		return stepBuilderFactory.get("step2")
			.flow(flow070301())
			.build();
	}
	@Bean
	public Step step070303() {
		return stepBuilderFactory.get("step3")
			.tasklet((contribution, chunkContext) -> {
				System.out.println(">> step3 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step step070304() {
		return stepBuilderFactory.get("step4")
			.tasklet((contribution, chunkContext) -> {
				System.out.println(">> step4 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step step070305() {
		return stepBuilderFactory.get("step5")
			.tasklet((contribution, chunkContext) -> {
				System.out.println(">> step5 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step step070306() {
		return stepBuilderFactory.get("step6")
			.tasklet((contribution, chunkContext) -> {
				System.out.println(">> step6 has executed");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
