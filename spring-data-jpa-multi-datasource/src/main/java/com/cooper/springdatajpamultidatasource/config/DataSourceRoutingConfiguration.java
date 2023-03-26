package com.cooper.springdatajpamultidatasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceRoutingConfiguration {

    private static final String MASTER_DATA_SOURCE = "masterDataSource";
    private static final String SLAVE_DATA_SOURCE = "slaveDataSource";
    private static final String MASTER_KEY = "master";
    private static final String SLAVE_KEY = "slave";
    private static final String ROUTING_DATA_SOURCE = "routingDataSource";
    private static final String LAZY_CONNECTION_DATA_SOURCE_PROXY = "lazyConnectionDataSourceProxy";

    @Bean(MASTER_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(SLAVE_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @DependsOn({MASTER_DATA_SOURCE, SLAVE_DATA_SOURCE})
    public DataSource routingDataSource(
            @Qualifier(MASTER_DATA_SOURCE) DataSource masterDataSource,
            @Qualifier(SLAVE_DATA_SOURCE) DataSource slaveDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MASTER_KEY, masterDataSource);
        dataSourceMap.put(SLAVE_KEY, slaveDataSource);

        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);

        return routingDataSource;
    }

    @Primary
    @Bean(LAZY_CONNECTION_DATA_SOURCE_PROXY)
    @DependsOn(ROUTING_DATA_SOURCE)
    public DataSource lazyConnectionDataSourceProxy(
            @Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    static class RoutingDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? SLAVE_KEY : MASTER_KEY;
        }

    }

}
