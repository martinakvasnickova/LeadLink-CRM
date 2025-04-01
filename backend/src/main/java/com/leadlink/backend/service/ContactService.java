package com.leadlink.backend.service;

import com.leadlink.backend.exception.ContactNotFoundException;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.repository.ContactRepository;
import com.leadlink.backend.repository.UserRepository;
import com.leadlink.backend.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public Contact createContact(Contact contact) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        contact.setUser(user);
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

    public List<Contact> getContactsForCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();
        return contactRepository.findByUserUsername(username);
    }
}
