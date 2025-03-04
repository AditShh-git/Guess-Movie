package com.MovieGuess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GameSession {
    private final Long dialogId;
    private int attemptsLeft;


    public void decrementAttempts() {
        attemptsLeft--;
    }


}
