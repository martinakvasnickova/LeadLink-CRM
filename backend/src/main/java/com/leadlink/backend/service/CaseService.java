package com.leadlink.backend.service;


import com.leadlink.backend.dto.CaseRequestDTO;
import com.leadlink.backend.exception.CaseNotFoundException;
import com.leadlink.backend.model.Cases;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.repository.CaseRepository;
import com.leadlink.backend.repository.UserRepository;
import com.leadlink.backend.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Servisní třída pro práci s obchodními případy (Cases).
 * Obsahuje logiku pro vytváření, načítání, aktualizaci a mazání případů.
 */

@Service
public class CaseService {

    private static final Logger logger = LoggerFactory.getLogger(CaseService.class);
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Vytvoří nový případ a přiřadí jej aktuálně přihlášenému uživateli.
     *
     * @param caseRequestDTO DTO objekt obsahující data nového případu
     * @return Vytvořený případ
     * @throws RuntimeException pokud uživatel není nalezen
     */
    public Cases createCase(CaseRequestDTO caseRequestDTO){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Uživatel nebyl nalezen: {}", username);
            throw new RuntimeException("User not found");
        }

        Cases newCase = new Cases();
        newCase.setName(caseRequestDTO.getName());
        newCase.setPrice(caseRequestDTO.getPrice());
        newCase.setUser(user);
        logger.info("Nový případ vytvořen uživatelem: {}", username);

        return caseRepository.save(newCase);
    }

    /**
     * Načte všechny existující případy v systému.
     *
     * @return Seznam všech případů
     */
    public List<Cases> getAllCases(){
        logger.info("Načítám všechny případy");
        return caseRepository.findAll();
    }

    /**
     * Načte případ podle jeho ID.
     *
     * @param id ID případu
     * @return Případ odpovídající danému ID
     * @throws CaseNotFoundException pokud případ není nalezen
     */
    public Cases getCaseById(Long id){
        logger.info("Načítám případ s ID: {}", id);
        return caseRepository.findById(id)
                .orElseThrow(()-> new CaseNotFoundException(id));
    }


    /**
     * Aktualizuje existující případ podle ID.
     *
     * @param id ID aktualizovaného případu
     * @param caseRequestDTO DTO obsahující nová data
     * @return Aktualizovaný případ
     * @throws CaseNotFoundException pokud případ není nalezen
     */
    public Cases updateCase(Long id, CaseRequestDTO caseRequestDTO){
        logger.info("Aktualizuji případ s ID: {}", id);
        return caseRepository.findById(id)
                .map(caseEntity -> {
                    caseEntity.setName(caseRequestDTO.getName());
                    caseEntity.setPrice(caseRequestDTO.getPrice());
                    return caseRepository.save(caseEntity);
                }).orElseThrow(() -> new CaseNotFoundException(id));
    }

    /**
     * Smaže případ podle jeho ID.
     *
     * @param id ID případu
     * @throws CaseNotFoundException pokud případ není nalezen
     */
    public void deleteCase(Long id){
        if(!caseRepository.existsById(id)){
            logger.error("Případ s ID {} pro smazání nebyl nalezen", id);
            throw new CaseNotFoundException(id);
        }
        logger.warn("Případ s ID {} byl smazán", id);
        caseRepository.deleteById(id);
    }

    /**
     * Načte případy aktuálně přihlášeného uživatele.
     *
     * @return Seznam případů patřících přihlášenému uživateli
     */
    public List<Cases>getCasesForCurrentUser(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();
        logger.info("Načítám případy pro uživatele: {}", username);
        return caseRepository.findByUserUsername(username);

    }

}
