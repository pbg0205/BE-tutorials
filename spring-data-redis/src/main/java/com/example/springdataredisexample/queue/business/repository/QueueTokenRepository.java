package com.example.springdataredisexample.queue.business.repository;

import java.util.UUID;

public interface QueueTokenRepository {
	Long enqueue(UUID tokenId);
	String dequeue();
}
