package com.lifyo.application.service;

import com.lifyo.application.port.in.LeadUseCases;
import com.lifyo.application.port.out.LeadRepositoryPort;
import com.lifyo.application.services.LeadService;
import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeadServiceTest {

    private LeadRepositoryPort leadRepositoryPort;
    private LeadUseCases leadService;

    @BeforeEach
    void setUp() {
        // Mock del repositorio
        leadRepositoryPort = mock(LeadRepositoryPort.class);

        // Servicio usando la implementación concreta de LeadUseCases
        leadService = new LeadService(leadRepositoryPort);

        // Simular que el repositorio devuelve el mismo Lead que se le pasa
        when(leadRepositoryPort.save(any(Lead.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldRegisterLeadAndCallRepository() {
        // Given
        Email email = new Email("user@lifyo.com");

        // When
        Lead lead = leadService.registerLead(email);

        // Then
        // Verificar que el repositorio se llamó exactamente una vez
        ArgumentCaptor<Lead> leadCaptor = ArgumentCaptor.forClass(Lead.class);
        verify(leadRepositoryPort, times(1)).save(leadCaptor.capture());

        Lead capturedLead = leadCaptor.getValue();

        assertNotNull(capturedLead.id());
        assertEquals(email, capturedLead.email());
        assertNotNull(capturedLead.createdAt());
        assertEquals(capturedLead, lead);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        Email email = new Email("existing@lifyo.com");
        Lead existingLead = Lead.newLead(email);

        when(leadRepositoryPort.findByEmail(email.value()))
                .thenReturn(Optional.of(existingLead));

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> leadService.registerLead(email));

        // Asegurar de que no se intente guardar nada
        verify(leadRepositoryPort, never()).save(any());
    }

}
