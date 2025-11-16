package com.lifyo.infrastructure.outbound.persistence.entities.mappers;

import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;
import com.lifyo.infrastructure.outbound.persistence.entities.LeadEntity;

public class LeadMapper {

    public static LeadEntity fromDomain(Lead lead) {
        return new LeadEntity(
                lead.id(),
                lead.email().value(),
                lead.createdAt()
        );
    }

    public static Lead toDomain(LeadEntity leadEntity) {
        return new Lead(
                leadEntity.getId(),
                new Email(leadEntity.getEmail()),
                leadEntity.getCreatedAt()
        );
    }

}
