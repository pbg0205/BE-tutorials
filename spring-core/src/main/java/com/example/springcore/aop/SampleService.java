package com.example.springcore.aop;

import org.springframework.stereotype.Component;

@Component
public class SampleService {

	@LogExecutionTime
	public void serve(String sapmle) throws InterruptedException {
		Thread.sleep(2000);
	}

}
