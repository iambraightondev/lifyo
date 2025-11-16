package com.lifyo.infrastructure.inbound.rest.dtos;

import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;

public record LeadRequestDto(
        String email
) {
    public static Lead toEntity(LeadRequestDto leadRequestDto) {
        return Lead.newLead(
                new Email(leadRequestDto.email)
        );
    }
}
