package org.example.demo.listeners;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageRecoverer implements MessageRecoverer {

	@Autowired
	private RabbitTemplate errorTemplate;

	@Override
	public void recover(Message message, Throwable cause) {
		if (isFatal(cause)) {
			errorTemplate.send("temp-ex", "temp", message);
		} else {
			if (!hasDeadLetterRetryLimitExceeded(message)) {
				message.getMessageProperties().setExpiration("20000");
				message.getMessageProperties().setPriority(9);
				errorTemplate.send("pubsub-audit-write-dlq-ex", "pubsub.audit.write", message);
			} else {
				errorTemplate.send("temp-ex", "temp", message);
			}
		}

	}

	private boolean isFatal(Throwable cause) {
		Boolean isFatal = Boolean.TRUE;
		if (cause instanceof ListenerExecutionFailedException) {
			cause = cause.getCause();
			if (cause instanceof PersistenceException)
				isFatal = Boolean.FALSE;
		}
		return isFatal;
	}

	private boolean hasDeadLetterRetryLimitExceeded(Message in) {
		List<Map<String, Object>> xDeathHeader = in.getMessageProperties().getHeader("x-death");
		if (xDeathHeader != null && xDeathHeader.size() >= 1) {
			Long count = (Long) xDeathHeader.get(0).get("count");
			if (count >= 3) {
				xDeathHeader.get(0).put("reason", "rejected");
				return true;
			}
		}
		return false;
	}

}