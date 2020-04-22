package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.B2BServiceRequest;
import com.example.demo.models.B2BServiceResponse;
import com.example.demo.models.MtnDetail;
import com.example.demo.services.B2BService;

@RestController
@RequestMapping(value = "b2bservice")
public class B2BController {

	@Autowired
	private B2BService b2bService;
	private final static Logger LOGGER = LoggerFactory.getLogger(B2BController.class);

	@PostMapping(value = "/fetchMtndetails")
	public B2BServiceResponse fetchMtndetails(@RequestBody final B2BServiceRequest b2bServiceRequest) {
		LOGGER.trace("Enter into fetchMtndetails method with parameters : {}", b2bServiceRequest);
		final MtnDetail mtnDetail = b2bService.getMtnDetails(b2bServiceRequest.getServiceNo());
		final B2BServiceResponse b2bServiceResponse = B2BServiceResponse.builder().mtnDetail(mtnDetail).build();
		LOGGER.trace("Exit from fetchMtnDetails method with output : {}", b2bServiceResponse);
		return b2bServiceResponse;
	}
}
