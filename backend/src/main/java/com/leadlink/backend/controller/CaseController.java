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

/**
 * Controller pro správu obchodních případů (Cases).
 * Zajišťuje CRUD operace a práci s případy uživatele.
 */
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/case")
public class CaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    /**
     * Endpoint pro vytvoření nového obchodního případu.
     *
     * @param caseRequestDTO DTO s daty případu
     * @return Vytvořený případ v ApiResponse obálce
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

    /**
     * Endpoint pro získání všech obchodních případů.
     *
     * @return Seznam případů ve formátu CaseDTO
     */
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
                            contactName,
                            businessCase.getUser() != null ? businessCase.getUser().getUsername() : null

                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Endpoint pro načtení konkrétního případu podle ID.
     *
     * @param id ID případu
     * @return Případ s daným ID
     */
    @GetMapping("/{id}")
    public Cases getCaseById(@PathVariable Long id){
        logger.info("Načítám případ s ID {}", id);
        return caseService.getCaseById(id);
    }

    /**
     * Endpoint pro aktualizaci případu podle ID.
     *
     * @param id             ID případu k aktualizaci
     * @param caseRequestDTO DTO s novými daty
     * @return Aktualizovaný případ
     */
    @PutMapping("/{id}")
    public Cases updateCase(@PathVariable Long id, @Valid @RequestBody CaseRequestDTO caseRequestDTO) {
        logger.info("Aktualizuji případ s ID {}", id);
        return caseService.updateCase(id, caseRequestDTO);
    }

    /**
     * Endpoint pro smazání případu podle ID.
     *
     * @param id ID případu ke smazání
     * @return Zpráva o úspěšném smazání
     */
    @DeleteMapping("/{id}")
    public String deleteCase(@PathVariable Long id){
        logger.warn("Mažu případ s ID {}", id);
        caseService.deleteCase(id);
        return "Case with id " + id + " has been deleted successfully.";
    }

    /**
     * Endpoint pro načtení všech případů přihlášeného uživatele.
     *
     * @return Seznam případů patřících aktuálnímu uživateli
     */

    /*
    @GetMapping("/user")
    public List<Cases> getCasesForCurrentUser(){
        logger.info("Načítám případy aktuálního uživatele");
        return caseService.getCasesForCurrentUser();
    }


    @GetMapping("/user")
    public List<CaseDTO> getCasesForCurrentUser() {
        logger.info("Načítám případy aktuálního uživatele");
        List<Cases> businessCases = caseService.getCasesForCurrentUser();
        return businessCases.stream()
                .map(businessCase -> {
                    String contactName = null;
                    if (!businessCase.getContactCases().isEmpty()) {
                        Contact contact = businessCase.getContactCases().get(0).getContact();
                        contactName = contact.getFirstname() + " " + contact.getLastname();
                    }
                    String username = businessCase.getUser() != null ? businessCase.getUser().getUsername() : null;
                    return new CaseDTO(
                            businessCase.getId(),
                            businessCase.getName(),
                            businessCase.getPrice(),
                            contactName,
                            username
                    );
                })
                .collect(Collectors.toList());
    }

     */

    @GetMapping("/user")
    public List<CaseDTO> getCasesForCurrentUser() {
        logger.info("Načítám případy aktuálního uživatele");
        List<Cases> businessCases = caseService.getCasesForCurrentUser();
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
                            contactName,
                            businessCase.getUser() != null ? businessCase.getUser().getUsername() : null
                    );
                })
                .collect(Collectors.toList());
    }

}
