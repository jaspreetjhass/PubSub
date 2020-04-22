package org.example.demo.configurations;

import javax.persistence.PersistenceException;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;

public class CustomExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

	@Override
	public boolean isFatal(Throwable t) {
		Boolean isFatal = Boolean.FALSE;
		if (!(t.getCause() instanceof PersistenceException))
			isFatal = Boolean.TRUE;
		return isFatal;
	}
}
