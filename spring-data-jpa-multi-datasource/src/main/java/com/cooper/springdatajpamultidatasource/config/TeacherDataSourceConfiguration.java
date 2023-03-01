package com.cooper.springdatajpamultidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TeacherDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.teacher")
    public DataSourceProperties teacherDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource teacherDataSource(
            @Qualifier("teacherDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

}
