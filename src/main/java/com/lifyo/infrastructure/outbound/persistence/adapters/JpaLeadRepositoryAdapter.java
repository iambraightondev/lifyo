package com.lifyo.infrastructure.outbound.persistence.adapters;

import com.lifyo.application.port.out.LeadRepositoryPort;
import com.lifyo.domain.lead.Lead;
import com.lifyo.infrastructure.outbound.persistence.entities.LeadEntity;
import com.lifyo.infrastructure.outbound.persistence.entities.mappers.LeadMapper;
import com.lifyo.infrastructure.outbound.persistence.repositories.SpringDataLeadRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaLeadRepositoryAdapter implements LeadRepositoryPort {

    private final SpringDataLeadRepository repository;

    public JpaLeadRepositoryAdapter(SpringDataLeadRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lead save(Lead lead) {

        LeadEntity entity = LeadMapper.fromDomain(lead);
        LeadEntity savedEntity = repository.save(entity);

        return LeadMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Lead> findById(UUID id) {
        return repository.findById(id)
                .map(LeadMapper::toDomain);
    }

    @Override
    public Optional<Lead> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(LeadMapper::toDomain);
    }
}
