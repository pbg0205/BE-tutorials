package com.cooper.springrabbitmq.runner;

import java.util.Scanner;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CliRunner implements CommandLineRunner {

	private static final Scanner SCANNER = new Scanner(System.in);
	private final AmqpAdmin amqpAdmin;

	@Override
	public void run(String... args) throws Exception {
		int command = inputCommand();

		while (command != 0) {
			if (command == 1) { // exchange 생성
				declareExchange();
			} else if (command == 2) { // exchange 제거
				deleteExchange();
			} else if (command == 3) { // queue 생성
				declareQueue();
			} else if (command == 4) { // queue 제거
				deleteQueue();
			} else if (command == 5) {
				declareBinding();
			} else if (command == 6) {
				deleteBinding();
			}

			command = inputCommand();
		}

	}

	private static int inputCommand() {
		StringBuilder builder = new StringBuilder()
			.append("명령어를 입력하세요.").append("\n")
			.append("1. exchange 생성").append("\n")
			.append("2. exchange 제거").append("\n")
			.append("3. queue 생성").append("\n")
			.append("4. queue 제거").append("\n")
			.append("5. binding 생성").append("\n")
			.append("6. binding 제거").append("\n");

		System.out.println(builder);

		return Integer.parseInt(SCANNER.nextLine());
	}

	private void declareExchange() {
		System.out.println("생성할 exchange 를 이름을 입력해주세요.");

		String exchangeName = SCANNER.nextLine();
		Exchange topicExchange = new TopicExchange(exchangeName);
		amqpAdmin.declareExchange(topicExchange);

		System.out.println("이름이 " + exchangeName + "인 exchange 를 생성 했습니다.");
	}

	private void deleteExchange() {
		System.out.println("제거할 exchange 를 이름을 입력해주세요.");

		String exchangeName = SCANNER.nextLine();
		boolean deleted = amqpAdmin.deleteExchange(exchangeName);

		if (deleted) {
			System.out.println("이름이 " + exchangeName + "인 exchange 를 제거 했습니다.");
		} else {
			System.out.println("이름이 " + exchangeName + "인 exchange 가 존재하지 않습니다.");
		}
	}

	private void declareQueue() {
		System.out.println("생성할 queue 를 이름을 입력해주세요.");

		String queueName = SCANNER.nextLine();
		Queue queue = new Queue(queueName);

		String declaredQueueName = amqpAdmin.declareQueue(queue);
		System.out.println("이름이 " + declaredQueueName + "인 queue 를 생성 했습니다.");
	}

	private void deleteQueue() {
		System.out.println("제거할 queue 를 이름을 입력해주세요.");

		String queueName = SCANNER.nextLine();
		boolean deleted = amqpAdmin.deleteQueue(queueName);

		if (deleted) {
			System.out.println("이름이 " + queueName + "인 queue 를 제거 했습니다.");
		} else {
			System.out.println("이름이 " + queueName + "인 queue 가 존재하지 않습니다.");
		}
	}

	private void declareBinding() {
		System.out.println("연결할 queue 를 이름을 입력해주세요.");

		String queueName = SCANNER.nextLine();
		Queue queue = new Queue(queueName);

		System.out.println("연결할 exchange 를 이름을 입력해주세요.");

		String exchangeName = SCANNER.nextLine();
		Exchange topicExchange = new TopicExchange(exchangeName);

		System.out.println("라우팅 키를 입력해주세요");
		String routingKey = SCANNER.nextLine();

		// 기존 queue, exchange 존재하면 binding 이 생성된다.
		Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey).noargs();
		amqpAdmin.declareBinding(binding);
	}

	private void deleteBinding() {
		System.out.println("연결된 queue 를 이름을 입력해주세요.");

		String queueName = SCANNER.nextLine();
		Queue queue = new Queue(queueName);

		System.out.println("연결된 exchange 를 이름을 입력해주세요.");

		String exchangeName = SCANNER.nextLine();
		Exchange topicExchange = new TopicExchange(exchangeName);

		System.out.println("라우팅 키를 입력해주세요");
		String routingKey = SCANNER.nextLine();

		// 기존 queue, exchange 존재하면 binding 이 생성된다.
		Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey).noargs();
		amqpAdmin.removeBinding(binding);
	}

}
