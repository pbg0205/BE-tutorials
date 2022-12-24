package com.example.batchpart11.chunk.processor;

import com.example.batchpart11.domain.Product;
import com.example.batchpart11.dto.ProductVO;
import com.example.batchpart11.mapper.ProductVOToProductMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Autowired
    private ProductVOToProductMapper productVOToProductMapper;

    @Override
    public Product process(ProductVO item) throws Exception {
        return productVOToProductMapper.toProduct(item);
    }

}
