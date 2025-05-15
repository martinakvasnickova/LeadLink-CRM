package com.leadlink.backend.controller;

import com.leadlink.backend.dto.ContactEventDTO;
import com.leadlink.backend.dto.ContactEventRequestDTO;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.model.ContactEvent;
import com.leadlink.backend.model.Events;
import com.leadlink.backend.service.ContactEventService;
import com.leadlink.backend.service.ContactService;
import com.leadlink.backend.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/contact-event")
public class ContactEventController {

    private static final Logger logger = LoggerFactory.getLogger(ContactEventController.class);

    private final ContactEventService contactEventService;
    private final ContactService contactService;
    private final EventService eventService;

    public ContactEventController(ContactEventService contactEventService, ContactService contactService, EventService eventService) {
        this.contactEventService = contactEventService;
        this.contactService = contactService;
        this.eventService = eventService;
    }

    @PostMapping
    public ContactEventDTO addContactEvent(@RequestBody @Valid ContactEventRequestDTO contactEventRequestDTO) {
        logger.info("Přidávám propojení kontaktu {} a eventu {}", contactEventRequestDTO.getContactId(), contactEventRequestDTO.getEventId());

        // TADY - NAČTI EXISTUJÍCÍ OBJEKTY
        Contact contact = contactService.getContactById(contactEventRequestDTO.getContactId());
        Events event = eventService.getEventById(contactEventRequestDTO.getEventId());

        ContactEvent contactEvent = new ContactEvent();
        contactEvent.setContact(contact);
        contactEvent.setEvent(event);

        ContactEvent savedContactEvent = contactEventService.addContactEvent(contactEvent);

        return mapToDTO(savedContactEvent);
    }


    @GetMapping
    public List<ContactEventDTO> getAllContactEvents() {
        logger.info("Načítám všechna propojení kontaktů a eventů");
        List<ContactEvent> contactEvents = contactEventService.getAllContactEvent();
        return contactEvents.stream().map(this::mapToDTO).toList();
    }

    @GetMapping("/contact/{contactId}")
    public List<ContactEventDTO> getContactEventsByContact(@PathVariable Long contactId) {
        logger.info("Načítám propojení kontaktu s ID {}", contactId);
        return contactEventService.getContactEventsByContact(contactId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/event/{eventId}")
    public List<ContactEventDTO> getContactEventsByEvent(@PathVariable Long eventId) {
        logger.info("Načítám propojení eventu s ID {}", eventId);
        return contactEventService.getContactEventsByEvent(eventId).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteContactEvent(@PathVariable Long id) {
        logger.warn("Mažu propojení kontaktu a eventu s ID {}", id);
        contactEventService.deleteContactEvent(id);
    }

    private ContactEventDTO mapToDTO(ContactEvent contactEvent) {
        return new ContactEventDTO(
                contactEvent.getId(),
                contactEvent.getContact() != null ? contactEvent.getContact().getId() : null,
                contactEvent.getEvent() != null ? contactEvent.getEvent().getId() : null
        );
    }
}
