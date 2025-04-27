package com.leadlink.backend.service;

import com.leadlink.backend.controller.CaseController;
import com.leadlink.backend.dto.UserRequestDTO;
import com.leadlink.backend.exception.ContactNotFoundException;
import com.leadlink.backend.exception.UserNotFoundException;
import com.leadlink.backend.model.Contact;
import com.leadlink.backend.model.Role;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Servisní vrstva pro správu uživatelů.
 * Zajišťuje registraci, autentizaci a CRUD operace s uživateli.
 */

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Vyhledá uživatele podle uživatelského jména.
     */

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Vytvoří běžného uživatele
     */
    public Users createUser(UserRequestDTO userRequestDTO) {
        Users user = new Users();
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(Role.USER);
        logger.info("Vytvářím nového uživatele: {}", user.getUsername());

        return userRepository.save(user);
    }


    /**
     * Vytvoří administrátora
     */
    public Users createAdmin(UserRequestDTO userRequestDTO) {
        Users user = new Users();
        user.setFirstname(userRequestDTO.getFirstname());
        user.setLastname(userRequestDTO.getLastname());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(Role.ADMIN);

        logger.info("Vytvářím nového administrátora: {}", user.getUsername());

        return userRepository.save(user);
    }

    /**
     * Načte všechny uživatele.
     */
    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Najde uživatele podle ID.
     */
    public Users getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    /**
     * Aktualizuje existujícího uživatele.
     */
    public Users updateUser(Long id, Users newUser){
        return userRepository.findById(id)
                .map(user ->{
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    user.setEmail(newUser.getEmail());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());

                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    /**
     * Smaže uživatele podle ID.
     */
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Ověří přihlašovací údaje uživatele.
     */
    public boolean authenticate(String username, String password){
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("The password is incorrect");
        }

        return true;
    }




}
