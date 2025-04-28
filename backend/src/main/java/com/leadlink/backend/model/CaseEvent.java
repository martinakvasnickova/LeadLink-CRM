package com.leadlink.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CaseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    @JsonIgnore
    private Cases cases;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnore
    private Events event;

    public CaseEvent() {}

    public CaseEvent(Cases cases, Events event) {
        this.cases = cases;
        this.event = event;
    }

    // Gettery a settery

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cases getCases() { return cases; }
    public void setCases(Cases cases) { this.cases = cases; }

    public Events getEvent() { return event; }
    public void setEvent(Events event) { this.event = event; }
}
