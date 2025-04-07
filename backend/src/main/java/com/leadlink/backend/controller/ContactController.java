package com.leadlink.backend.controller;

import com.leadlink.backend.dto.ContactDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ContactDTO> getAllContacts(){
        List<Contact> contacts = contactService.getAllContacts();

        return contacts.stream()
                .map(contact ->{
                    String caseName = null;
                    if(!contact.getContactCases().isEmpty()){
                        Cases cases = contact.getContactCases().get(0).getCases();
                        caseName = cases.getName();
                    }
                    return new ContactDTO(
                            contact.getId(),
                            contact.getFirstname(),
                            contact.getLastname(),
                            contact.getEmail(),
                            caseName
                    );
                })
                .collect(Collectors.toList());
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

    @GetMapping("/user")
    public List<Contact> getContactsForCurrentUser() {
        return contactService.getContactsForCurrentUser();
    }

}
