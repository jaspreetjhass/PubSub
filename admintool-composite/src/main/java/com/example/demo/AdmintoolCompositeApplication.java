package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients(basePackages = { "com.example.demo.feign.clients" })
@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
public class AdmintoolCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmintoolCompositeApplication.class, args);
	}

}
