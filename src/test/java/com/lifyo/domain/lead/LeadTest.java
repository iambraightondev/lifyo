package com.lifyo.domain.lead;

import com.lifyo.domain.lead.value_objects.Email;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LeadTest {

    @Test
    void shouldCreateLeadWithValidEmail() {
        // Given
        Email email = new Email("user@lifyo.com");

        // When
        Lead lead = Lead.newLead(email);

        // Then
        assertNotNull(lead.id());
        assertEquals(email, lead.email());
        assertNotNull(lead.createdAt());
    }

    @Test
    void shouldGenerateUniqueIdsForEachLead() {
        // Given
        Email email1 = new Email("first@lifyo.com");
        Email email2 = new Email("second@lifyo.com");

        // When
        Lead lead1 = Lead.newLead(email1);
        Lead lead2 = Lead.newLead(email2);

        // Then
        assertNotEquals(lead1.id(), lead2.id());
    }

    @Test
    void shouldAcceptManualCreationIfNeeded() {
        // Given
        UUID id = UUID.randomUUID();
        Email email = new Email("manual@lifyo.com");
        Instant createdAt = Instant.now();

        // When
        Lead lead = new Lead(id,email, createdAt);

        // Then
        assertEquals(id, lead.id());
        assertEquals(email, lead.email());
        assertEquals(createdAt, lead.createdAt());
    }

}
