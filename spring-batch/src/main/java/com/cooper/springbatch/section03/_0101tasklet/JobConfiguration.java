package com.cooper.springbatch.section03._0101tasklet;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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

import com.cooper.springbatch.section03._0102_listener.JobResultListener;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job01")
			.start(step1())
			.next(step2())
			.listener(new JobResultListener())
			.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
			.tasklet(new Tasklet() {
				@Override
				public RepeatStatus execute(final StepContribution stepContribution,
					final ChunkContext chunkContext) throws Exception {
					System.out.println("step1 was executed.");

					// JobParameter 주입1
					JobParameters jobParameters01 = stepContribution.getStepExecution()
						.getJobExecution()
						.getJobParameters();

					System.out.println("user : " + jobParameters01.getString("name"));
					System.out.println("age : " + jobParameters01.getDouble("age"));
					System.out.println("date : " + jobParameters01.getDate("date"));

					// JobParameter 주입2
					Map<String, Object> jobParameters02 = chunkContext.getStepContext().getJobParameters();

					System.out.println("user : " + jobParameters02.get("name"));
					System.out.println("age : " + jobParameters02.get("age"));
					System.out.println("date : " + jobParameters02.get("date"));

					return RepeatStatus.FINISHED;
				}
			})
			.build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
			.tasklet((stepContribution, chunkContext) -> {
				System.out.println("step2 was executed.");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

}
