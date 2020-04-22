package com.example.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.models.MtnDetail;

@Service
public class B2BService {

	private static List<MtnDetail> mtnDetails = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(B2BService.class);

	static {
		mtnDetails = new ArrayList<>(
				Arrays.asList(MtnDetail.builder().serviceNo("1").accountNo("11").effectiveStartDate(new Date()).build(),
						MtnDetail.builder().serviceNo("2").accountNo("12").effectiveStartDate(new Date())
								.effectiveEndDate(new Date()).build(),
						MtnDetail.builder().serviceNo("3").accountNo("13").effectiveStartDate(new Date()).build(),
						MtnDetail.builder().serviceNo("4").accountNo("14").effectiveStartDate(new Date()).build()));
	}

	public MtnDetail getMtnDetails(final String serviceNo) {
		LOGGER.trace("Enter into getMtnDetails method with parameters : {}", serviceNo);
		MtnDetail output = null;
		final Optional<MtnDetail> optional = mtnDetails.parallelStream()
				.filter(mtndetail -> mtndetail.getServiceNo().equals(serviceNo)).findFirst();
		if (optional.isPresent())
			output = optional.get();
		LOGGER.trace("Exit from getMtnDetails method with output : {}", output);
		return output;
	}
}
