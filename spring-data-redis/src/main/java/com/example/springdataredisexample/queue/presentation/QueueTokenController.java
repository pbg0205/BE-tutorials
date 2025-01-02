package com.example.springdataredisexample.queue.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdataredisexample.queue.business.QueueTokenService;
import com.example.springdataredisexample.queue.dto.QueueTokenResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QueueTokenController {

	private final QueueTokenService queueTokenService;

	@PostMapping("/api/queue/token/enqueue")
	public ResponseEntity<QueueTokenResponse> issueToken() {
		return ResponseEntity.ok().body(queueTokenService.enqueue());
	}

	@PostMapping("/api/queue/token/dequeue")
	public ResponseEntity<String> removeToken() {
		return ResponseEntity.ok().body(queueTokenService.dequeue());
	}
}
