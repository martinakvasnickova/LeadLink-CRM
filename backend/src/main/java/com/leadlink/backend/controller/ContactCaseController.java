package com.leadlink.backend.controller;

import com.leadlink.backend.dto.ContactCaseDTO;
import com.leadlink.backend.dto.ContactCaseRequestDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.model.ContactCase;
import com.leadlink.backend.service.CaseService;
import com.leadlink.backend.service.ContactCaseService;
import com.leadlink.backend.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact-case")
public class ContactCaseController {

    private static final Logger logger = LoggerFactory.getLogger(ContactCaseController.class);

    private final ContactCaseService contactCaseService;
    private final ContactService contactService;
    private final CaseService caseService;

    public ContactCaseController(ContactCaseService contactCaseService, ContactService contactService, CaseService caseService) {
        this.contactCaseService = contactCaseService;
        this.contactService = contactService;
        this.caseService = caseService;
    }

    @PostMapping
    public ResponseEntity<ContactCaseDTO> addContactCase(@RequestBody @Valid ContactCaseRequestDTO contactCaseRequestDTO) {
        logger.info("Přidávám propojení kontaktu {} a případu {}", contactCaseRequestDTO.getContactId(), contactCaseRequestDTO.getCaseId());

        Contact contact = contactService.getContactById(contactCaseRequestDTO.getContactId());
        Cases cases = caseService.getCaseById(contactCaseRequestDTO.getCaseId());

        ContactCase contactCase = new ContactCase();
        contactCase.setContact(contact);
        contactCase.setCases(cases);
        contactCase.setRole(contactCaseRequestDTO.getRole());

        ContactCase savedContactCase = contactCaseService.addContactCase(contactCase);

        return ResponseEntity.ok(mapToDTO(savedContactCase));
    }

    @GetMapping
    public List<ContactCaseDTO> getAllContactCases() {
        logger.info("Načítám všechna propojení kontaktů a případů");
        return contactCaseService.getAllContactCases()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/contact/{contactId}")
    public List<ContactCaseDTO> getContactCasesByContact(@PathVariable Long contactId) {
        logger.info("Načítám propojení kontaktu s ID {}", contactId);
        return contactCaseService.getContactCasesByContact(contactId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/case/{caseId}")
    public List<ContactCaseDTO> getContactCasesByCase(@PathVariable Long caseId) {
        logger.info("Načítám propojení případu s ID {}", caseId);
        return contactCaseService.getContactCasesByCase(caseId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteContactCase(@PathVariable Long id) {
        logger.warn("Mažu propojení s ID {}", id);
        contactCaseService.deleteContactCase(id);
    }

    private ContactCaseDTO mapToDTO(ContactCase contactCase) {
        return new ContactCaseDTO(
                contactCase.getId(),
                contactCase.getContact() != null ? contactCase.getContact().getId() : null,
                contactCase.getContact() != null ? contactCase.getContact().getFirstname() : null,
                contactCase.getContact() != null ? contactCase.getContact().getLastname() : null,
                contactCase.getCases() != null ? contactCase.getCases().getId() : null,
                contactCase.getCases() != null ? contactCase.getCases().getName() : null,
                contactCase.getRole()
        );
    }

}

