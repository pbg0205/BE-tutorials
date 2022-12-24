package com.example.batchpart11.domain;

import com.example.batchpart11.dto.ProductVO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRequestVO {

    private long id;
    private ProductVO productVO;
    private ApiResponseVO apiResponseVO;

}
