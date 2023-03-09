package com.cooper.springrestdocs.business;

import com.cooper.springrestdocs.dto.SampleResponse;

public interface SampleService {
    SampleResponse findSampleById(String id);
}
