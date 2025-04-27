package com.leadlink.backend.controller;

import com.leadlink.backend.dto.ApiResponse;
import com.leadlink.backend.dto.CaseDTO;
import com.leadlink.backend.dto.CaseRequestDTO;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.service.CaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/case")
public class CaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    /*
    @PostMapping
    public Cases newCase(@RequestBody Cases newCase){
        return caseService.createCase(newCase);
    }


    @PostMapping
    public Cases newCase(@Valid @RequestBody CaseRequestDTO caseRequestDTO) {
        return caseService.createCase(caseRequestDTO);
    }

     */

    @PostMapping
    public ResponseEntity<ApiResponse<Cases>> newCase(@Valid @RequestBody CaseRequestDTO caseRequestDTO) {
        Cases createdCase = caseService.createCase(caseRequestDTO);
        logger.info("Vytvářím nový obchodní případ: {}", caseRequestDTO.getName());


        ApiResponse<Cases> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Case created successfully",
                createdCase,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CaseDTO> getAllCases() {
        logger.info("Načítám všechny obchodní případy");
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
        logger.info("Načítám případ s ID {}", id);
        return caseService.getCaseById(id);
    }

    /*
    @PutMapping("/{id}")
    public Cases updateCase(@RequestBody Cases newCase, @PathVariable Long id){
        return caseService.updateCase(id, newCase);
    }

     */

    @PutMapping("/{id}")
    public Cases updateCase(@PathVariable Long id, @Valid @RequestBody CaseRequestDTO caseRequestDTO) {
        logger.info("Aktualizuji případ s ID {}", id);
        return caseService.updateCase(id, caseRequestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteCase(@PathVariable Long id){
        logger.warn("Mažu případ s ID {}", id);
        caseService.deleteCase(id);
        return "Case with id " + id + " has been deleted successfully.";
    }

    @GetMapping("/user")
    public List<Cases> getCasesForCurrentUser(){
        logger.info("Načítám případy aktuálního uživatele");
        return caseService.getCasesForCurrentUser();
    }
}
