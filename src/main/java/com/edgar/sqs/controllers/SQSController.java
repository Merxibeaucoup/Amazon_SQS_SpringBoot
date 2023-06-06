package com.edgar.sqs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sqs")
@Slf4j
public class SQSController {

	@Autowired
	private QueueMessagingTemplate messagingTemplate;

	@Value("${cloud.aws.endpoint.uri}")
	private String endpoint;
	

	@GetMapping("/send")
	public void sendMessageToQueue(@RequestParam String message) {
		messagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());	
	}
	
	@SqsListener("merxi-queue")
	public void loadMessageFromSQS(String message) {
		log.info("message from SQS Queue {} ", message);
	}

}
