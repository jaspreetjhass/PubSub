package com.example.demo.models;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDetails implements Serializable {

	private static final long serialVersionUID = -6134204147586430279L;
	private String serviceNo;
	private String accountNo;
	private Date effectiveStartDate;
	private Date effectiveEndDate;

}
