package com.MovieGuess.controller;

import com.MovieGuess.dto.LogInRequest;
import com.MovieGuess.dto.RegisterRequest;
import com.MovieGuess.dto.UserDto;
import com.MovieGuess.service.JwtService;
import com.MovieGuess.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) { // Assuming RegisterRequest is DTO
        try{
            userService.saveUser(registerRequest);
            return ResponseEntity.ok("User registered successfully!");
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LogInRequest logInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword()));

    final UserDetails userDetails = userService.findByUsername(logInRequest.getUsername());
    final String jwt = jwtService.generateToken(userDetails);
    return ResponseEntity.ok(jwt);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication){

        String username = authentication.getName();
        UserDto userDto = userService.getUserDtoByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/my-score")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> getCurrentUserScore(Authentication authentication){
        String username = authentication.getName();
        int score = userService.getScoreByUsername(username);
        return ResponseEntity.ok(score);
    }
}