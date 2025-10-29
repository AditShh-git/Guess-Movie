package com.MovieGuess.controller;

import com.MovieGuess.service.GameService;
import com.MovieGuess.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    @GetMapping("/start")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> startGame(Authentication authentication) {
        String username = authentication.getName();
        return new ResponseEntity<>(gameService.startGame(username), HttpStatus.OK);
    }

    @PostMapping("/guess")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> verifyGuess(Authentication authentication, @RequestParam String userGuess) {
        String username = authentication.getName();
        return ResponseEntity.ok(gameService.verifyGuess(username, userGuess));
    }

    @PostMapping("/restartGame")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> restartGame(Authentication authentication) {
        String username = authentication.getName();
        return new ResponseEntity<>(gameService.restartGame(username), HttpStatus.OK);
    }


}
