package com.example.demo.feign.clients;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

public class CommonFeignConfig {

	@Bean
	public Contract contract() {
		return new SpringMvcContract();
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

	@Bean
	public Request.Options requestOptions(@Value("${http.connection.timeout}") final Long connectionTimeout,
			@Value("${http.read.timeout}") final Long readTimeout) {
		return new Request.Options(readTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, false);
	}

	@Bean
	public Retryer retryer(@Value("${feign.period}") final Long period, @Value("${feign.maxPeriod}") final Long maxPeriod,
			@Value("${feign.maxAttempt}") final Integer maxAttempt) {
		return new Retryer.Default(period, maxPeriod, maxAttempt);
	}
	
	@Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
