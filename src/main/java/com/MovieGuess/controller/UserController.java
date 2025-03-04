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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;


    private final AuthenticationManager authenticationManager;

    // POST endpoint for creating a new user
    @PostMapping("/register")
    public String save(@Valid @RequestBody User user) {
        try {
            userService.saveUser(user);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Error registering user: " + e.getMessage();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogInRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userService.findByUsername(loginRequest.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    // GET endpoint for fetching user details by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // PUT endpoint for updating user score
    @PutMapping("/updateScore/{userId}")
    public String updateScore(@PathVariable Long userId, @RequestParam int points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setScore(user.getScore() + points);
        userRepository.save(user);
        return "User score updated successfully!";
    }

}