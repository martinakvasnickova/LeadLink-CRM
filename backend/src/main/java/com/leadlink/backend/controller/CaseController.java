package com.leadlink.backend.controller;

import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.CaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Cases> getAllCases(){
        return caseService.getAllCases();
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
        return "Case with id " + id + " has been deleted successfuly.";
    }

    @GetMapping("/case")
    public List<Cases> getCasesForCurrentUser(){
        return caseService.getCasesForCurrentUser();
    }
}
