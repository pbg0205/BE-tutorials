package com.cooper.springrestdocs.business;

import com.cooper.springrestdocs.dto.SampleResponse;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {

    @Override
    public SampleResponse findSampleById(String id) {
        return new SampleResponse(id, "sample_name");
    }

}
