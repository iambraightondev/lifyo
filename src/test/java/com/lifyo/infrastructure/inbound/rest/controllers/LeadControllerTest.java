package com.lifyo.infrastructure.inbound.rest.controllers;

import com.lifyo.application.port.in.LeadUseCases;
import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LeadController.class)
@AutoConfigureMockMvc(addFilters = false)
class LeadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeadUseCases leadUseCases;

    @Test
    void shouldCreateLeadSuccessfully() throws Exception {
        // Given
        String emailValue = "test@lifyo.com";
        Email email = new Email(emailValue);
        Lead lead = Lead.newLead(email);

        when(leadUseCases.registerLead(email)).thenReturn(lead);

        // When + Then
        mockMvc.perform(post("/api/v1/leads/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + emailValue + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(emailValue));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        // Given
        String invalidEmail = "not-an-email";

        doThrow(new IllegalArgumentException("Invalid email format"))
                .when(leadUseCases).registerLead(any(Email.class));

        // When + Then
        mockMvc.perform(post("/api/v1/leads/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + invalidEmail + "\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        // Given
        String emailValue = "existing@lifyo.com";
        Email email = new Email(emailValue);

        doThrow(new IllegalStateException("Lead with this email already exists"))
                .when(leadUseCases).registerLead(email);

        // When + Then
        mockMvc.perform(post("/api/v1/leads/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + emailValue + "\"}"))
                .andExpect(status().isConflict());
    }

}
