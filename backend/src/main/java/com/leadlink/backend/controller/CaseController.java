package com.leadlink.backend.controller;

import com.leadlink.backend.dto.CaseDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.CaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/case")
public class CaseController {
    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping
    public Cases newCase(@RequestBody Cases newCase){
        return caseService.createCase(newCase);
    }

    @GetMapping
    public List<CaseDTO> getAllCases() {
        List<Cases> businessCases = caseService.getAllCases();

        return businessCases.stream()
                .map(businessCase -> {
                    String contactName = null;
                    if (!businessCase.getContactCases().isEmpty()) {
                        Contact contact = businessCase.getContactCases().get(0).getContact();
                        contactName = contact.getFirstname() + " " + contact.getLastname();
                    }
                    return new CaseDTO(
                            businessCase.getId(),
                            businessCase.getName(),
                            businessCase.getPrice(),
                            contactName
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Cases getCaseById(@PathVariable Long id){
        return caseService.getCaseById(id);
    }

    @PutMapping("/{id}")
    public Cases updateCase(@RequestBody Cases newCase, @PathVariable Long id){
        return caseService.updateCase(id, newCase);
    }

    @DeleteMapping("/{id}")
    public String deleteCase(@PathVariable Long id){
        caseService.deleteCase(id);
        return "Case with id " + id + " has been deleted successfully.";
    }

    @GetMapping("/case")
    public List<Cases> getCasesForCurrentUser(){
        return caseService.getCasesForCurrentUser();
    }
}
