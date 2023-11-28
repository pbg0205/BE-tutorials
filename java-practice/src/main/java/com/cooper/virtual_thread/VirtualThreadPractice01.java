package com.cooper.virtual_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadPractice01 {
	public static void main(String[] args) throws Exception {
		run();
	}

	public static void run() throws Exception {

		// Virtual Thread 방법 1
		Thread.startVirtualThread(() -> {
			System.out.println("Hello Virtual Thread");
		});

		// Virtual Thread 방법 2
		Runnable runnable = () -> System.out.println("Hi Virtual Thread");
		Thread virtualThread1 = Thread.ofVirtual().start(runnable);

		// Virtual Thread 이름 지정
		Thread.Builder builder = Thread.ofVirtual().name("JVM-Thread");
		Thread virtualThread2 = builder.start(runnable);

		// 스레드가 Virtual Thread인지 확인하여 출력
		System.out.println("Thread is Virtual? " + virtualThread2.isVirtual());

		// ExecutorService 사용
		try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
			for (int i = 0; i <3; i++) {
				executorService.submit(runnable);
			}
		}
	}
}
