package org.example.demo.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class AuditDetailsEntity implements Serializable {

	private static final long serialVersionUID = -3151093851357663508L;

	@EmbeddedId
	private AuditPk auditPk;
	@Column
	private Date effectiveEndDate;

}
