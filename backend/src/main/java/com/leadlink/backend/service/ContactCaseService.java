package com.leadlink.backend.service;

import com.leadlink.backend.model.ContactCase;
import com.leadlink.backend.repository.ContactCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactCaseService {
    private final ContactCaseRepository contactCaseRepository;

    public ContactCaseService(ContactCaseRepository contactCaseRepository) {
        this.contactCaseRepository = contactCaseRepository;
    }

    public ContactCase addContactCase(ContactCase contactCase) {
        return contactCaseRepository.save(contactCase);
    }

    public List<ContactCase> getAllContactCases() {
        return contactCaseRepository.findAll();
    }

    public List<ContactCase> getContactCasesByContact(Long contactId) {
        return contactCaseRepository.findByContactId(contactId);
    }

    public List<ContactCase> getContactCasesByCase(Long casesId) {
        return contactCaseRepository.findByCasesId(casesId);
    }

    public void deleteContactCase(Long id) {
        contactCaseRepository.deleteById(id);
    }
}
