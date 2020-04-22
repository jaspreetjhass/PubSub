package com.example.demo.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtnDetail {

	private String serviceNo;
	private String accountNo;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	
}
