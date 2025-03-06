package com.MovieGuess.controller;

import com.MovieGuess.service.GameService;
import com.MovieGuess.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final JwtService jwtService;

    @GetMapping("/start/{username}")
    public ResponseEntity<?> startGame(@PathVariable String username) {
        String tokenUsername = jwtService.getUsername(token.replace("Bearer ", ""));

        return new ResponseEntity<>(gameService.startGame(username), HttpStatus.OK);
    }

    @PostMapping("/guess/{username}")
    public ResponseEntity<?> verifyGuess(@PathVariable String username, @RequestParam String userGuess) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.verifyGuess(username, userGuess));

    }

    @PostMapping("/restartGame/{username}")
    public ResponseEntity<String> restartGame(@PathVariable String username) {
        return new ResponseEntity<>(gameService.restartGame(username), HttpStatus.OK);
    }


}
