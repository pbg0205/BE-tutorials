package com.example.batchpart11.service;

import com.example.batchpart11.domain.ApiInfo;
import com.example.batchpart11.domain.ApiResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService2 extends AbstractApiService{

    @Override
    public ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo){

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/api/product/2", apiInfo, String.class);

        int statusCodeValue = response.getStatusCodeValue();

        return new ApiResponseVO(statusCodeValue + "", response.getBody());
    }

}
