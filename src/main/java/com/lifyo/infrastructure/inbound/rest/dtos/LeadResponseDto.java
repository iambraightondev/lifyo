package com.lifyo.infrastructure.inbound.rest.dtos;

import com.lifyo.domain.lead.Lead;

import java.time.Instant;
import java.util.UUID;

public record LeadResponseDto(
        UUID id,
        String email,
        Instant createdAt
) {
    public static LeadResponseDto fromEntity(Lead lead) {
        return new LeadResponseDto(
                lead.id(),
                lead.email().value(),
                lead.createdAt()
        );
    }
}
