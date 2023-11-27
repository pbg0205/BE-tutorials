package com.cooper.virtual_thread;

public class VirtualThread01 {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = Thread.ofVirtual().start(() -> System.out.println("Hello"));
		thread.join();
	}
}
