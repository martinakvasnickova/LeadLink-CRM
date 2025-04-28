package com.leadlink.backend.dto;

import jakarta.validation.constraints.NotNull;

public class ContactCaseRequestDTO {

    @NotNull(message = "Contact ID must not be null")
    private Long contactId;

    @NotNull(message = "Case ID must not be null")
    private Long caseId;

    private String role;

    public ContactCaseRequestDTO() {}

    public ContactCaseRequestDTO(Long contactId, Long caseId, String role) {
        this.contactId = contactId;
        this.caseId = caseId;
        this.role = role;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
