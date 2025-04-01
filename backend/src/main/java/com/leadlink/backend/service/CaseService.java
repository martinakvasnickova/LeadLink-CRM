package com.leadlink.backend.service;


import com.leadlink.backend.exception.CaseNotFoundException;
import com.leadlink.backend.model.Case;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.repository.CaseRepository;
import com.leadlink.backend.repository.UserRepository;
import com.leadlink.backend.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Case createCase(Case caseEntity){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userPrincipal.getUsername();

        Users user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        caseEntity.setUser(user);
        return caseRepository.save(caseEntity);
    }

    public List<Case> getAllCases(){
        return caseRepository.findAll();
    }

    public Case getCaseById(Long id){
        return caseRepository.findById(id)
                .orElseThrow(()-> new CaseNotFoundException(id));
    }

    public Case updateCase(Long id, Case newCase){
        return caseRepository.findById(id)
                .map(caseEntity ->{
                    caseEntity.setName(newCase.getName());
                    caseEntity.setPrice(newCase.getPrice());
                    return caseRepository.save(caseEntity);
                }).orElseThrow(()->new CaseNotFoundException(id));
    }

}
