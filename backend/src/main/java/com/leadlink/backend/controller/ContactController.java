package com.leadlink.backend.controller;

import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public Contact newContact(@RequestBody Contact newContact) {
        return contactService.createContact(newContact);
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@RequestBody Contact newContact, @PathVariable Long id) {
        return contactService.updateContact(id, newContact);
    }

    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "Contact with id " + id + " has been deleted successfully.";
    }

    @GetMapping("/contact/user")
    public List<Contact> getContactsForCurrentUser() {
        return contactService.getContactsForCurrentUser();
    }

}
