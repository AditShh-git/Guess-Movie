package com.MovieGuess.controller;

import com.MovieGuess.service.GameService;
import com.MovieGuess.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<?> startGame(Authentication authentication) {
        String username = authentication.getName();
        return new ResponseEntity<>(gameService.startGame(username), HttpStatus.OK);
    }

    @PostMapping("/guess")
    public ResponseEntity<?> verifyGuess(Authentication authentication, @RequestParam String userGuess) { // Removed path variable
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(gameService.verifyGuess(username, userGuess));

    }

    @PostMapping("/restartGame")
    public ResponseEntity<String> restartGame(Authentication authentication) {
        String username = authentication.getName();
        return new ResponseEntity<>(gameService.restartGame(username), HttpStatus.OK);
    }
}
