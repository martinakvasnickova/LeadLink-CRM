package com.leadlink.backend.service;

import com.leadlink.backend.exception.ContactNotFoundException;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    public Contact updateContact(Long id, Contact newContact) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setFirstname(newContact.getFirstname());
                    contact.setLastname(newContact.getLastname());
                    contact.setEmail(newContact.getEmail());
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new ContactNotFoundException(id));
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ContactNotFoundException(id);
        }
        contactRepository.deleteById(id);
    }
}
