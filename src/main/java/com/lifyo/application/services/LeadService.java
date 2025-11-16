package com.lifyo.application.services;

import com.lifyo.application.port.in.LeadUseCases;
import com.lifyo.application.port.out.LeadRepositoryPort;
import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;
import org.springframework.stereotype.Service;

@Service
public class LeadService implements LeadUseCases {

    private final LeadRepositoryPort leadRepositoryPort;

    public LeadService(LeadRepositoryPort leadRepositoryPort) {
        this.leadRepositoryPort = leadRepositoryPort;
    }

    @Override
    public Lead registerLead(Email email) {
        // Validar si ya existe
        if (leadRepositoryPort.findByEmail(email.value()).isPresent()) {
            throw new IllegalArgumentException("Lead with this email already exists");
        }

        Lead newLead = Lead.newLead(email);

        return leadRepositoryPort.save(newLead);
    }

}
