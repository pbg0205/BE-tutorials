package com.cooper.virtual_thread;

import java.time.Duration;
import java.util.concurrent.Executors;

public class VirtualThreadCreateElapsedPractice {
	public static void main(String[] args) throws Exception {
		run();
	}

	public static void run() throws Exception {
		while (true) {
			long start = System.currentTimeMillis();
			try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
				// 10만개의 Virtual Thread 실행
				for (int i = 0; i < 100_000; i++) {
					executor.submit(() -> {
						Thread.sleep(Duration.ofSeconds(2));
						return null;
					});
				}

			}
			long end = System.currentTimeMillis();
			System.out.println((end - start) + "ms");
		}
	}
}
