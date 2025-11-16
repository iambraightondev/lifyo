package com.lifyo.infrastructure.inbound.rest.controllers;

import com.lifyo.application.port.in.LeadUseCases;
import com.lifyo.domain.lead.Lead;
import com.lifyo.infrastructure.inbound.rest.dtos.LeadRequestDto;
import com.lifyo.infrastructure.inbound.rest.dtos.LeadResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leads")
public class LeadController {

    private final LeadUseCases leadUseCases;

    public LeadController(LeadUseCases leadUseCases) {
        this.leadUseCases = leadUseCases;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public LeadResponseDto createLead(@RequestBody LeadRequestDto leadRequestDto) {

        Lead lead = LeadRequestDto.toEntity(leadRequestDto);

        Lead createdLead = leadUseCases.registerLead(lead.email());

        return LeadResponseDto.fromEntity(createdLead);
    }

}
