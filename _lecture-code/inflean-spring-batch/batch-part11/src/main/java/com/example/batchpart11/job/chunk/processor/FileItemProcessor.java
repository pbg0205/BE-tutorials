package com.example.batchpart11.job.chunk.processor;

import com.example.batchpart11.domain.Product;
import com.example.batchpart11.dto.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) throws Exception {
        return null;
    }

}
