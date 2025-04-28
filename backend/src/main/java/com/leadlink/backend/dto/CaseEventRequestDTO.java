package com.leadlink.backend.dto;

import jakarta.validation.constraints.NotNull;

public class CaseEventRequestDTO {
    @NotNull
    private Long caseId;
    @NotNull
    private Long eventId;

    // Gettery a settery
    public Long getCaseId() { return caseId; }
    public void setCaseId(Long caseId) { this.caseId = caseId; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
