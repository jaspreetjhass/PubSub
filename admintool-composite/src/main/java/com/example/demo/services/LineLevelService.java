package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.constants.AppConstant;
import com.example.demo.exceptions.AdminToolException;
import com.example.demo.feign.clients.B2BServiceClient;
import com.example.demo.models.B2BServiceRequest;
import com.example.demo.models.B2BServiceResponse;
import com.example.demo.models.ServiceDetails;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class LineLevelService {

	@Autowired
	private B2BServiceClient b2bServiceClient;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	private static final Logger LOGGER = LoggerFactory.getLogger(LineLevelService.class);

	@Transactional
	public ServiceDetails processLine(final String serviceNo, final String userId) throws JsonProcessingException {
		LOGGER.trace("Enter into getServiceDetails method with parameters : {}", serviceNo);

		final B2BServiceRequest b2bServiceRequest = B2BServiceRequest.builder().serviceNo(serviceNo).userId(userId)
				.build();
		final B2BServiceResponse b2bServiceResponse = b2bServiceClient.getServiceDetails(b2bServiceRequest);
		final ServiceDetails serviceDetails = b2bServiceResponse.getMtnDetail();

		MessagePostProcessor messagePostProcessor = (msg) -> {
			msg.getMessageProperties().setContentType(AppConstant.CONTENT_TYPE);
			msg.getMessageProperties().setAppId(AppConstant.APP_ID);
			msg.getMessageProperties().setType(AppConstant.REQUEST_TYPE);
			msg.getMessageProperties()
					.setMessageId(String.valueOf(LocalDateTime.now().getLong(ChronoField.MILLI_OF_SECOND)));
			msg.getMessageProperties().setHeader(AppConstant.USER_ID, userId);
			msg.getMessageProperties().getHeaders().remove(AppConstant.TYPE_ID);
			msg.getMessageProperties().setTimestamp(new Date());
			msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
			return msg;
		};
		try {
			rabbitTemplate.convertAndSend("pubsub-audit-write-ex", "pubsub.audit.write", serviceDetails,
					messagePostProcessor);
		} catch (AmqpException amqpException) {
			throw new AdminToolException("exception occured while connecting to rabbit mq");
		}
		LOGGER.trace("Exit from getServiceDetails method with output : {}", serviceDetails);
		return serviceDetails;
	}
}
