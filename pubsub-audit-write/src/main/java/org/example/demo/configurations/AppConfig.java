package org.example.demo.configurations;

import org.aopalliance.aop.Advice;
import org.example.demo.listeners.CustomMessageRecoverer;
import org.example.demo.models.AuditDetails;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.util.ErrorHandler;

@Configuration
public class AppConfig {

	@Bean
	public DefaultClassMapper typeMapper() {
		DefaultClassMapper typeMapper = new DefaultClassMapper();
		typeMapper.setDefaultType(AuditDetails.class);
		/*
		 * Map<String,Class<?>> map = new HashMap<>();
		 * map.put("com.example.demo.models.ServiceDetails", AuditDetails.class);
		 * typeMapper.setIdClassMapping(map);
		 */
		return typeMapper;
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter(DefaultClassMapper classMapper) {
		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
		converter.setClassMapper(classMapper);
		return converter;
	}

	@Primary
	@Bean
	public ErrorHandler errorHandler() {
		return new CustomErrorHandler();
	}

	@Bean
	CustomExceptionStrategy customExceptionStrategy() {
		return new CustomExceptionStrategy();
	}

	@Bean
	public RetryOperationsInterceptor statefulRetryOperationsInterceptor(RabbitTemplate rabbitTemplate,
			CustomMessageRecoverer recoverer) {
		return RetryInterceptorBuilder.StatefulRetryInterceptorBuilder.stateless().maxAttempts(1).recoverer(recoverer)
				.build();
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setAddresses("localhost:5672");
		cachingConnectionFactory.setUsername("guest");
		cachingConnectionFactory.setPassword("guest");
		return cachingConnectionFactory;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ErrorHandler errorHandler,
			ConnectionFactory connectionFactory, RetryOperationsInterceptor retryOperationsInterceptor) {
		SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
		containerFactory.setErrorHandler(errorHandler);
		containerFactory.setConnectionFactory(connectionFactory);
		containerFactory.setAdviceChain(new Advice[] { retryOperationsInterceptor });
		return containerFactory;
	}

}