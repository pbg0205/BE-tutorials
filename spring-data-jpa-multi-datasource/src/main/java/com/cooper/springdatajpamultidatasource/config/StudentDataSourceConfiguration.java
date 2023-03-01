package com.cooper.springdatajpamultidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class StudentDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.student")
    public DataSourceProperties studentDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @Primary
    public DataSource studentDataSource(
            @Qualifier("studentDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

}
