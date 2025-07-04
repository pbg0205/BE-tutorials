package com.cooper.springsse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
@Slf4j
public class SseSubscribeController {

	// 클라이언트 연결을 저장하기 위한 리스트 (간단한 예제에서는 static)
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter connect(@RequestParam String userName) {
		// 타임아웃 30분
		SseEmitter emitter = new SseEmitter(30 * 1000L);
		emitters.put(userName, emitter);

		log.info("Connected to SSE: size - {}", emitters.size());

		try {
			emitter.send(SseEmitter.event().name("init").data("connected"));
			log.info("send first message");
		} catch (IOException e) {
			emitter.completeWithError(e);
		}

		// 연결이 끊어졌을 때 리스트에서 제거
		emitter.onCompletion(() -> {
			emitters.remove(emitter);
			log.info("Disconnected from SSE: size - {}", emitters.size());
		});
		emitter.onTimeout(() -> {
			emitters.remove(emitter);
			log.info("Disconnected from SSE: {}", emitters.size());
		});
		emitter.onError((e) -> {
			emitters.remove(emitter);
			log.info("Disconnected from SSE: {}", emitters.size());
		});

		return emitter;
	}

	// 테스트용: 메시지 브로드캐스트
	@PostMapping("/send")
	public ResponseEntity<String> send(@RequestParam String message) {
		for (SseEmitter emitter : emitters.values()) {
			try {
				emitter.send(SseEmitter.event().name("message").data(message));
			} catch (IOException e) {
				emitters.remove(emitter);
			}
		}
		return ResponseEntity.ok("sent");
	}
}
