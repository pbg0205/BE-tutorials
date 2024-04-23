package com.cooper.springrabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.cooper.springrabbitmq.chat.vo.ChatMessage;

@Slf4j
@Component
public class RabbitmqListener {

	@RabbitListener(messageConverter = "jsonMessageConverter")
	public void processMessage(ChatMessage chatMessage) {
		log.debug("chatMessage : {}", chatMessage);
	}

}
