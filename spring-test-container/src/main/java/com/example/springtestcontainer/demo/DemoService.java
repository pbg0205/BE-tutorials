package com.example.springtestcontainer.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoRepository demoRepository;

    public DemoEntity getDemoEntity(Long id) {
        return demoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
    }
}
