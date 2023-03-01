package com.cooper.springdatajpamultidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.cooper.springdatajpamultidatasource.teacher.repository",
        entityManagerFactoryRef = "teacherEntityManagerFactory",
        transactionManagerRef = "teacherTransactionManager"
)
public class TeacherJpaConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean teacherEntityManagerFactory(
            @Qualifier("teacherDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.cooper.springdatajpamultidatasource.teacher.domain")
                .persistenceUnit("teacherEntityManager")
                .build();
    }

    @Bean
    public PlatformTransactionManager teacherTransactionManager(
            @Qualifier("teacherEntityManagerFactory") LocalContainerEntityManagerFactoryBean teacherEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(teacherEntityManagerFactory.getObject()));
    }

}
