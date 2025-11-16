package com.lifyo.infrastructure.outbound.persistence.repositories;

import com.lifyo.infrastructure.outbound.persistence.entities.LeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataLeadRepository extends JpaRepository<LeadEntity, UUID> {

    Optional<LeadEntity> findByEmail(String email);
}
