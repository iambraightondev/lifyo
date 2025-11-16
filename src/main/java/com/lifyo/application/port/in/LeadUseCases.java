package com.lifyo.application.port.in;

import com.lifyo.domain.lead.Lead;
import com.lifyo.domain.lead.value_objects.Email;

public interface LeadUseCases {

    Lead registerLead(Email email);

}
