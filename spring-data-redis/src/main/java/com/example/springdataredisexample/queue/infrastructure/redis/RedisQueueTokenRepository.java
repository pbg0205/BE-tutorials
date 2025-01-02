package com.example.springdataredisexample.queue.infrastructure.redis;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.springdataredisexample.queue.business.repository.QueueTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisQueueTokenRepository implements QueueTokenRepository {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public Long enqueue(UUID tokenId) {
		System.out.println("tokenId = " + tokenId);
		redisTemplate.opsForList().rightPush("queue", tokenId.toString());
		return redisTemplate.opsForList().indexOf("queue", tokenId.toString());
	}

	@Override
	public String dequeue() {
		return redisTemplate.opsForList().leftPop("queue");
	}
}
