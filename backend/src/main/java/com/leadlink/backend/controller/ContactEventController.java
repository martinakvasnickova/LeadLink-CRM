package com.leadlink.backend.controller;

import com.leadlink.backend.model.ContactEvent;
import com.leadlink.backend.service.ContactEventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact-event")
public class ContactEventController {
    private final ContactEventService contactEventService;

    public ContactEventController(ContactEventService contactEventService) {
        this.contactEventService = contactEventService;
    }

    @PostMapping
    public ContactEvent addContactEvent(@RequestBody ContactEvent contactEvent){
        return contactEventService.addContactEvent(contactEvent);
    }

    @GetMapping
    public List<ContactEvent> getAllContactEvent(){
        return contactEventService.getAllContactEvent();
    }

    @GetMapping("/contact/{contactId}")
    public List<ContactEvent> getContactEventsByContact(@PathVariable Long contactId){
        return contactEventService.getContactEventsByContact(contactId);
    }

    @GetMapping("/event/{eventId}")
    public List<ContactEvent> getContactEventsByEvent(@PathVariable Long eventId){
        return contactEventService.getContactEventsByEvent(eventId);
    }

    @DeleteMapping("/{id}")
    public void deleteContactEvent(@PathVariable Long id){
        contactEventService.deleteContactEvent(id);
    }


}
