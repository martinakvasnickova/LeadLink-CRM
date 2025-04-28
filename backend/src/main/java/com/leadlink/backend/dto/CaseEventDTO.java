package com.leadlink.backend.dto;

public class CaseEventDTO {
    private Long id;
    private Long caseId;
    private String caseName;
    private Long eventId;
    private String eventName;

    public CaseEventDTO(Long id, Long caseId, String caseName, Long eventId, String eventName) {
        this.id = id;
        this.caseId = caseId;
        this.caseName = caseName;
        this.eventId = eventId;
        this.eventName = eventName;
    }

    // Gettery
    public Long getId() { return id; }
    public Long getCaseId() { return caseId; }
    public String getCaseName() { return caseName; }
    public Long getEventId() { return eventId; }
    public String getEventName() { return eventName; }
}
