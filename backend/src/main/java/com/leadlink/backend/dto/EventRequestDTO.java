package com.leadlink.backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class EventRequestDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Start time must not be null")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startAt;

    @NotNull(message = "End time must not be null")
    @Future(message = "End time must be in the future")
    private LocalDateTime endAt;

    public EventRequestDTO() {}

    public EventRequestDTO(String name, LocalDateTime startAt, LocalDateTime endAt) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}
