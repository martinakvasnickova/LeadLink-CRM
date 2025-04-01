package com.leadlink.backend.controller;

import com.leadlink.backend.model.ContactCase;
import com.leadlink.backend.service.ContactCaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact-case")
public class ContactCaseController {
    private final ContactCaseService contactCaseService;

    public ContactCaseController(ContactCaseService contactCaseService) {
        this.contactCaseService = contactCaseService;
    }

    @PostMapping
    public ContactCase addContactCase(@RequestBody ContactCase contactCase) {
        return contactCaseService.addContactCase(contactCase);
    }

    @GetMapping
    public List<ContactCase> getAllContactCases() {
        return contactCaseService.getAllContactCases();
    }

    @GetMapping("/contact/{contactId}")
    public List<ContactCase> getContactCasesByContact(@PathVariable Long contactId) {
        return contactCaseService.getContactCasesByContact(contactId);
    }

    @GetMapping("/case/{caseId}")
    public List<ContactCase> getContactCasesByCase(@PathVariable Long caseId) {
        return contactCaseService.getContactCasesByCase(caseId);
    }

    @DeleteMapping("/{id}")
    public void deleteContactCase(@PathVariable Long id) {
        contactCaseService.deleteContactCase(id);
    }
}

