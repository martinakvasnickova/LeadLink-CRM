package com.leadlink.backend.controller;

import com.leadlink.backend.model.Contact;
import com.leadlink.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    Contact newContact(@RequestBody Contact newContact){
        return contactRepository.save(newContact);
    }

    @GetMapping("/contacts")
    List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

}
