package com.example.springdataredisexample.queue.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.springdataredisexample.queue.business.repository.QueueTokenRepository;
import com.example.springdataredisexample.queue.dto.QueueTokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueueTokenService {

	private final QueueTokenRepository queueTokenRepository;

	public QueueTokenResponse enqueue() {
		UUID tokenId = UUID.randomUUID();
		final Long index = queueTokenRepository.enqueue(tokenId);
		return new QueueTokenResponse(tokenId, index);
	}

	public String dequeue() {
		return queueTokenRepository.dequeue();
	}
}
