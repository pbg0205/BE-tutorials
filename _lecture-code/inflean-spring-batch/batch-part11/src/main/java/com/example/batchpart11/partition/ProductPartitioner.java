package com.example.batchpart11.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
