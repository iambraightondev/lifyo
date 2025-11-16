package com.lifyo.application.port.out;

import com.lifyo.domain.lead.Lead;

import java.util.Optional;
import java.util.UUID;

public interface LeadRepositoryPort {

    Lead save(Lead lead);

    Optional<Lead> findById(UUID id);

    Optional<Lead> findByEmail(String email);

}
