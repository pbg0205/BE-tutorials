package com.cooper.springdatajpabasic.product.controller;

import com.cooper.springdatajpabasic.product.dto.ProductCreateRequestDto;
import com.cooper.springdatajpabasic.product.dto.ProductCreateResponseDto;
import com.cooper.springdatajpabasic.product.dto.ProductDeleteRequestDto;
import com.cooper.springdatajpabasic.product.dto.ProductLookupDto;
import com.cooper.springdatajpabasic.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductLookupDto> findById(Long id) {
        ProductLookupDto productLookupDto = productService.findById(id);
        return ResponseEntity.ok(productLookupDto);
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponseDto> createProduct(
            @RequestBody ProductCreateRequestDto productCreateRequestDto
    ) {
        ProductCreateResponseDto productCreateResponseDto = productService.createProduct(productCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCreateResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestBody ProductDeleteRequestDto productDeleteRequestDto) {
        productService.deleteProduct(productDeleteRequestDto.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
