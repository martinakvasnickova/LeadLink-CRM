package com.leadlink.backend.service;

import com.leadlink.backend.model.ContactEvent;
import com.leadlink.backend.repository.ContactEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactEventService {
    private final ContactEventRepository contactEventRepository;

    public ContactEventService(ContactEventRepository contactEventRepository) {
        this.contactEventRepository = contactEventRepository;
    }

    public ContactEvent addContactEvent(ContactEvent contactEvent){
        return contactEventRepository.save(contactEvent);
    }

    public List<ContactEvent> getAllContactEvent(){
        return contactEventRepository.findAll();
    }

    public List<ContactEvent> getContactEventsByContact(Long contactId){
        return contactEventRepository.findByContactId(contactId);
    }

    public List<ContactEvent> getContactEventsByEvent(Long eventId){
        return contactEventRepository.findByEventId(eventId);
    }

    public void deleteContactEvent(Long id){
        contactEventRepository.deleteById(id);
    }
}
