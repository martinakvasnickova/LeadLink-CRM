package com.leadlink.backend.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String username;   // vlastník eventu (uživatel)
    private Long caseId;       // připojený případ
    private String caseName;   // název případu

    public EventDTO(Long id, String name, LocalDateTime createdAt, LocalDateTime startAt, LocalDateTime endAt, String username, Long caseId, String caseName) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.username = username;
        this.caseId = caseId;
        this.caseName = caseName;
    }

    // Gettery a Settery

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public String getUsername() {
        return username;
    }

    public Long getCaseId() {
        return caseId;
    }

    public String getCaseName() {
        return caseName;
    }
}
