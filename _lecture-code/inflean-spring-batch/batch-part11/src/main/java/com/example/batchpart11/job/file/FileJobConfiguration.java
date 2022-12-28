package com.example.batchpart11.job.file;

import com.example.batchpart11.domain.Product;
import com.example.batchpart11.dto.ProductVO;
import com.example.batchpart11.chunk.processor.FileItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
                .start(fileStep1())
                .build();
    }

    @Bean
    public Step fileStep1() {
        return stepBuilderFactory.get("fileStep1")
                .<ProductVO, Product>chunk(10)
                .reader(fileItemReader(null))
                .processor(fileItemProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile")
                .resource(new ClassPathResource("product_" + requestDate +".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("id","name","price","type")
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    /**
     * @ref 8. Spring Batch 가이드 - ItemWriter : https://jojoldu.tistory.com/339
     * [ItemWriter]
     * 1. ItemWriter : Spring Batch 에서 제공하는 출력 기능
     * 2. SpringBatch2 부터 ItemWriter 는 item 하나를 작성하지 않고 Chunk 단위로 묶인 item List 를 다룬다.
     * 3. 즉, Reader 와 Processor 를 거쳐 처리된 Item 을 Chunk 단위 만큼 쌓은 뒤 이를 Writer 에 전달하는 것이다.
     * 4. ItemWriter 의 종류로는 JdbcBatchItemWriter, HibernateItemWriter, JpaItemWriter 가 있다.
     *
     * [JpaItemWriter]
     * 1. Writer 에 전달하는 데이터가 Entity 클래스라면 JpaItemWriter 를 사용한다.
     * 2. JpaItemWriter 는 JPA 를 사용하기 때문에 영속성 관리를 위해 EntityManager 를 할당해줘야 한다.
     * 3. JdbcBatchItemWriter 에 비해 필수값이 Entity Manager 뿐이라 체크할 요소가 적다.
     *
     * - execution param argument 로 --job.name=fileJob requestDate=20210101 추가해서 테스트 진행 (나중에 리드미로 옮기자..)
     */
    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

}
