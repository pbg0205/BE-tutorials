package com.cooper.springrabbitmq.chat.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ChatMessage {
	private final String name;
	private final String message;
}
