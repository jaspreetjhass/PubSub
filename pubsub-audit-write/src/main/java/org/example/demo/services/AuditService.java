package org.example.demo.services;

import org.example.demo.models.AuditDetailsEntity;
import org.example.demo.repositories.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

	@Autowired
	private AuditRepository auditRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditService.class);

	public AuditDetailsEntity saveAuditDetailsEntity(final AuditDetailsEntity auditDetailsEntity) {
		LOGGER.trace("Enter into saveAuditDetailsEntity method with parameters : {}", auditDetailsEntity);
		return auditRepository.save(auditDetailsEntity);
	}

}
