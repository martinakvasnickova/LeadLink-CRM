package com.leadlink.backend.dto;

import jakarta.validation.constraints.NotNull;

public class ContactEventRequestDTO {

    @NotNull(message = "Contact ID must not be null")
    private Long contactId;

    @NotNull(message = "Event ID must not be null")
    private Long eventId;

    public ContactEventRequestDTO() {}

    public ContactEventRequestDTO(Long contactId, Long eventId) {
        this.contactId = contactId;
        this.eventId = eventId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
