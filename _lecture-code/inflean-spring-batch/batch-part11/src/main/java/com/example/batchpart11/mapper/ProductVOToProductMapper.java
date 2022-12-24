package com.example.batchpart11.mapper;

import com.example.batchpart11.domain.Product;
import com.example.batchpart11.dto.ProductVO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductVOToProductMapper {

    Product toProduct(ProductVO productVO);

}
