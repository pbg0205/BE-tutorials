package com.example.springdataredisexample.queue.dto;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class QueueTokenResponse {
	private final UUID tokenId;
	private final Long position;
}
