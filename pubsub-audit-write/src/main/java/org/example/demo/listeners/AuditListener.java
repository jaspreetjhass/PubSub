package org.example.demo.listeners;

import java.sql.SQLException;

import org.example.demo.models.AuditDetails;
import org.example.demo.models.AuditDetailsEntity;
import org.example.demo.models.AuditPk;
import org.example.demo.services.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {

	@Autowired
	private AuditService auditService;
	@Autowired
	private Jackson2JsonMessageConverter converter;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditListener.class);

	@RabbitListener(queues = "pubsub-audit-write-queue", containerFactory = "simpleRabbitListenerContainerFactory")
	public void receiveMessage(final Message message) throws SQLException {
		LOGGER.trace("Enter into receiveMessage method with parameters : {}", message, message.getMessageProperties());
		// ObjectMapper mapper = new ObjectMapper();
		AuditDetails auditDetails = (AuditDetails) converter.fromMessage(message);
		final AuditPk auditPk = AuditPk.builder().serviceNo(auditDetails.getServiceNo())
				.accountNo(auditDetails.getAccountNo()).effectiveStartDate(auditDetails.getEffectiveStartDate())
				.build();
		final AuditDetailsEntity auditDetailsEntity = AuditDetailsEntity.builder().auditPk(auditPk)
				.effectiveEndDate(auditDetails.getEffectiveEndDate()).build();
		auditService.saveAuditDetailsEntity(auditDetailsEntity);

		LOGGER.trace("Exit from receiveMessage method");
	}

}
