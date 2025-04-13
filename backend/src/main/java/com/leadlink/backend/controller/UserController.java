package com.leadlink.backend.controller;

import com.leadlink.backend.model.JwtResponse;
import com.leadlink.backend.model.LoginRequest;
import com.leadlink.backend.model.Users;
import com.leadlink.backend.security.JwtService;
import com.leadlink.backend.security.JwtUtil;
import com.leadlink.backend.security.UserPrincipal;
import com.leadlink.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> newUser(@RequestBody() Users user){
        Users newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

            if (isAuthenticated) {
                Users user = userService.findByUsername(loginRequest.getUsername());
                UserPrincipal userDetails = new UserPrincipal(user); // <- konverze na UserDetails

                // Debug log
                System.out.println("User authenticated: " + userDetails.getUsername());

                String token = jwtService.generateToken(userDetails); // generování tokenu
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

}


