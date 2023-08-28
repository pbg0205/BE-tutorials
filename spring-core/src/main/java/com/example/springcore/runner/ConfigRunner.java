package com.example.springcore.runner;

import com.example.springcore.configurationproperties.ConfigProperties01;
import com.example.springcore.configurationproperties.ConfigProperties02;
import com.example.springcore.configurationproperties.ConfigProperties03;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigRunner implements CommandLineRunner {

    private final ConfigProperties01 configProperties01;
    private final ConfigProperties02 configProperties02;
    private final ConfigProperties03 configProperties03;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("configProperties01 = " + configProperties01);
        System.out.println("configProperties02 = " + configProperties02);
        System.out.println("configProperties03 = " + configProperties03);
    }

}
