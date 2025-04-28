package com.leadlink.backend.dto;

public class ContactEventDTO {

    private Long id;
    private Long contactId;
    private Long eventId;

    public ContactEventDTO(Long id, Long contactId, Long eventId) {
        this.id = id;
        this.contactId = contactId;
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public Long getContactId() {
        return contactId;
    }

    public Long getEventId() {
        return eventId;
    }
}
