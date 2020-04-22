package org.example.demo.models;

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
public class AuditDetails implements Serializable {

	private static final long serialVersionUID = -3151093851357663508L;
	private String serviceNo;
	private String accountNo;
	private Date effectiveStartDate;
	private Date effectiveEndDate;

}
