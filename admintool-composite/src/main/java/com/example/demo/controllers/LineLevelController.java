package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.LineLevelRequest;
import com.example.demo.models.LineLevelResponse;
import com.example.demo.models.ServiceDetails;
import com.example.demo.services.LineLevelService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "mbt/admintool")
public class LineLevelController {

	@Autowired
	private LineLevelService lineLevelService;
	private static final Logger LOGGER = LoggerFactory.getLogger(LineLevelController.class);

	@PostMapping(value = "/fixMtndetails")
	public LineLevelResponse fixMtnDetails(@RequestBody final LineLevelRequest lineLevelRequest) throws JsonProcessingException {
		LOGGER.trace("Enter into fixMtnDetails method with parameters : {}", lineLevelRequest);
		
		final ServiceDetails serviceDetails = lineLevelService.processLine(lineLevelRequest.getMtn(),lineLevelRequest.getUserId());
		final LineLevelResponse lineLevelResponse = LineLevelResponse.builder().details(serviceDetails).build();
		
		LOGGER.trace("Exit from fixMtnDetails method with output : {}", lineLevelResponse);
		return lineLevelResponse;
	}
}
