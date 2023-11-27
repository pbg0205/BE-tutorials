package com.cooper.virtual_thread;

public class VirtualThread02 {
	public static void main(String[] args) throws InterruptedException {
		Thread.Builder builder = Thread.ofVirtual().name("MyThread");
		Runnable task = () -> {
			System.out.println("Running thread");
		};
		Thread t1 = builder.start(task);
		System.out.println("Thread t1 name: " + t1.getName());
		t1.join();
	}
}
