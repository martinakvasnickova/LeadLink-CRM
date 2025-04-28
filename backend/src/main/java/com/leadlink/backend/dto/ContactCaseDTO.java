package com.leadlink.backend.dto;

public class ContactCaseDTO {

    private Long id;
    private Long contactId;
    private String contactFirstname;
    private String contactLastname;
    private Long caseId;
    private String caseName;
    private String role;

    public ContactCaseDTO(Long id, Long contactId, String contactFirstname, String contactLastname, Long caseId, String caseName, String role) {
        this.id = id;
        this.contactId = contactId;
        this.contactFirstname = contactFirstname;
        this.contactLastname = contactLastname;
        this.caseId = caseId;
        this.caseName = caseName;
        this.role = role;
    }

    // Gettery
    public Long getId() {
        return id;
    }

    public Long getContactId() {
        return contactId;
    }

    public String getContactFirstname() {
        return contactFirstname;
    }

    public String getContactLastname() {
        return contactLastname;
    }

    public Long getCaseId() {
        return caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getRole() {
        return role;
    }
}
