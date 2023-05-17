package com.cooper.springrestdocs.presentation;

import com.cooper.springrestdocs.business.SampleService;
import com.cooper.springrestdocs.dto.SampleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/{sampleId}")
    public ResponseEntity<SampleResponse> findSampleById(@PathVariable String sampleId,
                                                         @RequestParam String reqParam1,
                                                         @RequestParam String reqParam2) {
        SampleResponse sampleResponse = sampleService.findSampleById(sampleId);
        return ResponseEntity.ok(sampleResponse);
    }

}
