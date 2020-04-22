package org.example.demo.repositories;

import org.example.demo.models.AuditDetailsEntity;
import org.example.demo.models.AuditPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<AuditDetailsEntity, AuditPk> {

}
