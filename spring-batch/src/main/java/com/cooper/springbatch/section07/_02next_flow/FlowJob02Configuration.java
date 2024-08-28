package com.cooper.springbatch.section07._02next_flow;

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

@Configuration
@RequiredArgsConstructor
public class FlowJob02Configuration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job batchJob02() {
		return jobBuilderFactory.get("batchJob0702")
			.start(flow070201())
			.next(step070203())
			.next(flow070202())
			.next(step070206())
			.end()
			.build();
	}

	private Flow flow070201() {
		final FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow070201");
		flowBuilder.start(step070201())
			.next(step070202())
			.end();

		return flowBuilder.build();
	}

	@Bean
	public Flow flow070202() {
		final FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow070202");
		flowBuilder.start(step070204())
			.next(step070205())
			.end();

		return flowBuilder.build();

	}

	@Bean
	public Step step070201() {
		return stepBuilderFactory.get("step070201")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step01 has executed");
				return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

	@Bean
	public Step step070202() {
		return stepBuilderFactory.get("step070202")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step02 has executed");
				return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

	@Bean
	public Step step070203() {
		return stepBuilderFactory.get("step070203")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step03 has executed");
				return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

	@Bean
	public Step step070204() {
		return stepBuilderFactory.get("step070204")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step04 has executed");
				throw new RuntimeException("step04 was failed");
				// return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

	@Bean
	public Step step070205() {
		return stepBuilderFactory.get("step070205")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step05 has executed");
				return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

	@Bean
	public Step step070206() {
		return stepBuilderFactory.get("step070206")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step06 has executed");
				return RepeatStatus.FINISHED; // SUCCESS
			})
			.build();
	}

}
