package org.example.demo.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class AuditPk implements Serializable {

	private static final long serialVersionUID = -1650461630729580505L;
	@Column
	private String serviceNo;
	@Column
	private String accountNo;
	@Column
	private Date effectiveStartDate;

}
