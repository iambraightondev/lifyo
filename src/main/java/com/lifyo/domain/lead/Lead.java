package com.lifyo.domain.lead;

import com.lifyo.domain.lead.value_objects.Email;

import java.time.Instant;
import java.util.UUID;

public record Lead(
        UUID id,
        Email email,
        Instant createdAt
) {
    public static Lead newLead(Email email) {
        return new Lead(
                UUID.randomUUID(),
                email,
                Instant.now()
        );
    }
}
