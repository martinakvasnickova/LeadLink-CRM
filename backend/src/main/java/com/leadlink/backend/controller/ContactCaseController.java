package com.leadlink.backend.controller;

import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.model.ContactCase;
import com.leadlink.backend.service.CaseService;
import com.leadlink.backend.service.ContactCaseService;
import com.leadlink.backend.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact-case")
public class ContactCaseController {

    /*
    private final ContactCaseService contactCaseService;


    public ContactCaseController(ContactCaseService contactCaseService) {
        this.contactCaseService = contactCaseService;
    }



    @PostMapping
    public ContactCase addContactCase(@RequestBody ContactCase contactCase) {
        return contactCaseService.addContactCase(contactCase);
    }

     */


    private final ContactCaseService contactCaseService;
    private final ContactService contactService;
    private final CaseService caseService;

    public ContactCaseController(ContactCaseService contactCaseService, ContactService contactService, CaseService caseService) {
        this.contactCaseService = contactCaseService;
        this.contactService = contactService;
        this.caseService = caseService;
    }

    @PostMapping
    public ResponseEntity<?> addContactCase(@RequestBody ContactCase contactCase) {
        if (contactCase.getContact() == null || contactCase.getContact().getId() == null ||
                contactCase.getCases() == null || contactCase.getCases().getId() == null) {
            return ResponseEntity.badRequest().body("Chybí ID kontaktu nebo ID případu.");
        }

        Contact contact = contactService.getContactById(contactCase.getContact().getId());
        Cases cases = caseService.getCaseById(contactCase.getCases().getId());

        contactCase.setContact(contact);
        contactCase.setCases(cases);

        ContactCase savedContactCase = contactCaseService.addContactCase(contactCase);
        return ResponseEntity.ok(savedContactCase);
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

