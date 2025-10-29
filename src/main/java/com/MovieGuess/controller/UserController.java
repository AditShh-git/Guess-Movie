package com.MovieGuess.controller;

import com.MovieGuess.dto.LogInRequest;
import com.MovieGuess.model.User;
import com.MovieGuess.repo.UserRepo;
import com.MovieGuess.service.GameService;
import com.MovieGuess.service.JwtService;
import com.MovieGuess.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // POST endpoint for creating a new user
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        // Use ResponseEntity for consistent responses
        try {
            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (IllegalStateException e) {
            // Handle specific exception for existing user
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Generic error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogInRequest loginRequest){
        // Authenticate using AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        // Fetch UserDetails via UserService
        final UserDetails userDetails = userService.findByUsername(loginRequest.getUsername());
        // Generate JWT
        final String jwt = jwtService.generateToken(userDetails);
        // Return token in response body
        return ResponseEntity.ok(jwt);
    }

    // GET endpoint for fetching user details by ID
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()") // Ensure user is logged in
    public ResponseEntity<User> getCurrentUserDetails(Authentication authentication) {
        // Get username from security context
        String username = authentication.getName();
        // Use UserService to find user (handles not found exception)
        User user = userService.findByUsername(username);
        // Return user details (password will likely be null or filtered by Jackson if configured)
        return ResponseEntity.ok(user);
    }



}