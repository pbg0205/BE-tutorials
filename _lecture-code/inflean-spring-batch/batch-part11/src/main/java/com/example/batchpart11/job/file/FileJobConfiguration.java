package com.example.batchpart11.job.file;

import com.example.batchpart11.domain.Product;
import com.example.batchpart11.dto.ProductVO;
import com.example.batchpart11.job.chunk.processor.FileItemProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private static final int CHUNK_SIZE = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("{jobParameter['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate + ".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("id", "name", "price", "type")
                .build();
    }

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep1())
                .build();
    }

    @Bean
    public Step fileStep1() {
        return stepBuilderFactory.get("fileStep1")
                .<ProductVO, Product>chunk(CHUNK_SIZE)
                .reader(fileItemReader(null))
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    /**
     * @ref 8. Spring Batch 가이드 - ItemWriter : https://jojoldu.tistory.com/339
     * 1. ItemWriter : Spring Batch 에서 제공하는 출력 기능
     * 2. SpringBatch2 부터 ItemWriter 는 item 하나를 작성하지 않고 Chunk 단위로 묶인 item List 를 다룬다.
     * 3. 즉, Reader 와 Processor 를 거쳐 처리된 Item 을 Chunk 단위 만큼 쌓은 뒤 이를 Writer 에 전달하는 것이다.
     * 4. ItemWriter 의 종류로는 JdbcBatchItemWriter, HibernateItemWriter, JpaItemWriter 가 있다.
     *
     * TODO @JpaItemWriter 정리
     */
    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

}
