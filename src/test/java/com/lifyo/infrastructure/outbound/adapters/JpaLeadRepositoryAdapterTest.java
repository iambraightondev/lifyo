package com.lifyo.infrastructure.outbound.adapters;

import com.lifyo.application.port.out.LeadRepositoryPort;
import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;
import com.lifyo.infrastructure.outbound.persistence.adapters.JpaLeadRepositoryAdapter;
import com.lifyo.infrastructure.outbound.persistence.entities.LeadEntity;
import com.lifyo.infrastructure.outbound.persistence.entities.mappers.LeadMapper;
import com.lifyo.infrastructure.outbound.persistence.repositories.SpringDataLeadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaLeadRepositoryAdapterTest {

    private LeadRepositoryPort jpaLeadRepositoryAdapter;
    private SpringDataLeadRepository springDataLeadRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        springDataLeadRepository = mock(SpringDataLeadRepository.class);
        jpaLeadRepositoryAdapter = new JpaLeadRepositoryAdapter(springDataLeadRepository);

        // Reinicia completamente el mock antes de cada test
        reset(springDataLeadRepository);
    }

    @Test
    void shouldSaveAndFindLeadByEmail() {
        // Given
        Email email = new Email("test@lifyo.com");
        Lead lead = Lead.newLead(email);
        LeadEntity entity = LeadMapper.fromDomain(lead);

        when(springDataLeadRepository.findByEmail(email.value()))
                .thenReturn(Optional.empty())  // primera llamada
                .thenReturn(Optional.of(entity)); // segunda llamada

        when(springDataLeadRepository.save(any(LeadEntity.class))).thenReturn(entity);

        // When
        jpaLeadRepositoryAdapter.save(lead);
        Optional<Lead> found = jpaLeadRepositoryAdapter.findByEmail(email.value());

        // Then
        assertTrue(found.isPresent());
        assertEquals(email, found.get().email());
    }

    @Test
    void shouldReturnEmptyWhenLeadNotFound() {
        when(springDataLeadRepository.findByEmail("nonexistent@lifyo.com")).thenReturn(Optional.empty());

        // When
        Optional<Lead> result = jpaLeadRepositoryAdapter.findByEmail("nonexistent@lifyo.com");

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldKeepSameIdAndCreatedAtAfterSave() {
        // Given
        Lead original = Lead.newLead(new Email("persisted@lifyo.com"));
        LeadEntity entity = LeadMapper.fromDomain(original);

        when(springDataLeadRepository.save(any(LeadEntity.class))).thenReturn(entity);

        // When
        Lead saved = jpaLeadRepositoryAdapter.save(original);

        // Then
        assertEquals(original.id(), saved.id());
        assertEquals(original.createdAt(), saved.createdAt());
        assertEquals(original.email(), saved.email());
    }

    @Test
    void shouldSaveMultipleLeadsWithUniqueEmails() {
        // Given
        Lead lead1 = Lead.newLead(new Email("a@lifyo.com"));
        Lead lead2 = Lead.newLead(new Email("b@lifyo.com"));

        LeadEntity entity1 = LeadMapper.fromDomain(lead1);
        LeadEntity entity2 = LeadMapper.fromDomain(lead2);

        when(springDataLeadRepository.save(any(LeadEntity.class)))
                .thenReturn(entity1)
                .thenReturn(entity2);

        // When
        Lead saved1 = jpaLeadRepositoryAdapter.save(lead1);
        Lead saved2 = jpaLeadRepositoryAdapter.save(lead2);

        // Then
        assertNotEquals(saved1.id(), saved2.id());
    }

    @Test
    void shouldNotAllowDuplicatedEmail() {
        // Given
        Email email = new Email("duplicate@lifyo.com");
        Lead first = Lead.newLead(email);
        Lead duplicate = Lead.newLead(email);

        LeadEntity entity = LeadMapper.fromDomain(first);

        when(springDataLeadRepository.findByEmail(email.value()))
                .thenReturn(Optional.of(entity));

        // Then
        assertThrows(IllegalStateException.class, () -> jpaLeadRepositoryAdapter.save(duplicate));
    }

}
