package com.cooper.springdatajpamultidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.cooper.springdatajpamultidatasource.student.repository",
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager"
)
public class StudentJpaConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(
            @Qualifier("studentDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.cooper.springdatajpamultidatasource.student.domain")
                .persistenceUnit("studentEntityManager")
                .build();
    }

    @Bean
    public PlatformTransactionManager studentTransactionManager(
            @Qualifier("studentEntityManagerFactory") LocalContainerEntityManagerFactoryBean studentEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(studentEntityManagerFactory.getObject()));
    }

}
