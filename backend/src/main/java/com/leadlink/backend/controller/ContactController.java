package com.leadlink.backend.controller;

import com.leadlink.backend.dto.ContactDTO;
import com.leadlink.backend.dto.ContactRequestDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.ContactService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public Contact newContact(@RequestBody ContactRequestDTO contactRequestDTO) {
        logger.info("Vytvářím nový kontakt: {} {}", contactRequestDTO.getFirstname(), contactRequestDTO.getLastname());
        Contact newContact = new Contact();
        newContact.setFirstname(contactRequestDTO.getFirstname());
        newContact.setLastname(contactRequestDTO.getLastname());
        newContact.setEmail(contactRequestDTO.getEmail());
        return contactService.createContact(newContact);
    }



    @GetMapping
    public List<ContactDTO> getAllContacts(){
        logger.info("Načítám všechny kontakty");
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
        logger.info("Načítám kontakt s ID {}", id);
        return contactService.getContactById(id);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@RequestBody ContactRequestDTO contactRequestDTO, @PathVariable Long id) {
        logger.info("Aktualizuji kontakt s ID {}", id);
        Contact updatedContact = new Contact();
        updatedContact.setFirstname(contactRequestDTO.getFirstname());
        updatedContact.setLastname(contactRequestDTO.getLastname());
        updatedContact.setEmail(contactRequestDTO.getEmail());
        return contactService.updateContact(id, updatedContact);
    }


    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable Long id) {
        logger.warn("Mažu kontakt s ID {}", id);
        contactService.deleteContact(id);
        return "Contact with id " + id + " has been deleted successfully.";
    }

    @GetMapping("/user")
    public List<Contact> getContactsForCurrentUser() {
        logger.info("Načítám kontakty aktuálního uživatele");
        return contactService.getContactsForCurrentUser();
    }

}
