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

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    /*
    public Cases createCase(Cases caseEntity){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        caseEntity.setUser(user);
        return caseRepository.save(caseEntity);
    }

     */

    public Cases createCase(CaseRequestDTO caseRequestDTO){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Cases newCase = new Cases();
        newCase.setName(caseRequestDTO.getName());
        newCase.setPrice(caseRequestDTO.getPrice());
        newCase.setUser(user);

        return caseRepository.save(newCase);
    }

    public List<Cases> getAllCases(){
        return caseRepository.findAll();
    }

    public Cases getCaseById(Long id){
        return caseRepository.findById(id)
                .orElseThrow(()-> new CaseNotFoundException(id));
    }

    /*
    public Cases updateCase(Long id, Cases newCase){
        return caseRepository.findById(id)
                .map(caseEntity ->{
                    caseEntity.setName(newCase.getName());
                    caseEntity.setPrice(newCase.getPrice());
                    return caseRepository.save(caseEntity);
                }).orElseThrow(()->new CaseNotFoundException(id));
    }

     */

    public Cases updateCase(Long id, CaseRequestDTO caseRequestDTO){
        return caseRepository.findById(id)
                .map(caseEntity -> {
                    caseEntity.setName(caseRequestDTO.getName());
                    caseEntity.setPrice(caseRequestDTO.getPrice());
                    return caseRepository.save(caseEntity);
                }).orElseThrow(() -> new CaseNotFoundException(id));
    }

    public void deleteCase(Long id){
        if(!caseRepository.existsById(id)){
            throw new CaseNotFoundException(id);
        }
        caseRepository.deleteById(id);
    }

    public List<Cases>getCasesForCurrentUser(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();
        return caseRepository.findByUserUsername(username);
    }

}
