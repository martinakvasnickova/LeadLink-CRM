package com.leadlink.backend.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String username;

    public EventDTO(Long id, String name, LocalDateTime createdAt, LocalDateTime startAt, LocalDateTime endAt, String username) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.username = username;
    }

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
}
