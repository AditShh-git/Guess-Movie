package com.MovieGuess.service;

import com.MovieGuess.dto.GameSession;
import com.MovieGuess.model.Dialog;
import com.MovieGuess.model.Movie;
import com.MovieGuess.model.User;
import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import com.MovieGuess.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GameService {

   private final DialogRepo dialogRepo;
   private final MovieRepo movieRepo;
   private final UserService userService;

   Map<String, GameSession> gameSessions = new ConcurrentHashMap<>();

   public String startGame(String username){
       if (gameSessions.containsKey(username)){
           return "You already have an active game! Use your remaining attempts.";
       }

       Optional<Dialog> dialog = dialogRepo.findRandomDialog();

       if (dialog.isPresent()){
           gameSessions.put(username, new GameSession(dialog.get().getId(), 5));
            return "Guess the movie for this dialogue: \"" + dialog.get().getDialogueText() + "\"";
       }
       return "No active dialog found";
   }

   public String verifyGuess(String username, String guess){
       GameSession gameSession = gameSessions.get(username);

       if (gameSession == null){
           return "You are not in a game!";
       }
       if (gameSession.getAttemptsLeft() <= 0){
           gameSessions.remove(username);
           return "You are out of guesses!";
       }

       Optional<Dialog> dialog = dialogRepo.findById(gameSession.getDialogId());
       if (dialog.isPresent()){
           Long mID = dialog.get().getMovie().getId();

           Optional<String> movie = movieRepo.findTitleByMovieId(mID);
           if (movie.isPresent() && movie.get().equalsIgnoreCase(guess)){
               userService.updateScore(1, username);
               gameSessions.remove(username);

               return "Correct guess " + username + " Your score is : " + userService.getScoreByUsername(username);
           } else {
               gameSession.decrementAttempts();
               return "Incorrect guess , Remaining attempts left : " + gameSession.getAttemptsLeft();
           }
       }
       return "Error fetching movie details.";
   }

    public String restartGame(String username) {

        System.out.println("Attempting to restart game for user: '" + username + "'");
        boolean sessionExists = gameSessions.containsKey(username);
        System.out.println("Session exists for user? " + sessionExists);

        if (!sessionExists) {
            System.out.println("Returning 'You are not in a game' because session doesn't exist.");
            return "You are not in a game";
        }

        System.out.println("Removing game session for user: '" + username + "'");
        gameSessions.remove(username);
        System.out.println("Session removed.");
        return "Game restarted successfully!";
    }
}
