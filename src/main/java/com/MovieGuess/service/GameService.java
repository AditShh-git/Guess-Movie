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

    private final DialogRepo dialogRepository;
    private final MovieRepo movieRepository;
    private final UserRepo userRepo;
//    private  User user;

    Map<String, GameSession> gameSessions = new ConcurrentHashMap<>();

    public String startGame(String username) {
        System.out.println("--- Starting Game for user: '" + username + "' ---"); // Log entry
        if (gameSessions.containsKey(username)) {
            System.out.println("User already has an active game."); // Log existing game
            return "You already have an active game! Use your remaining attempts.";
        }

        Optional<Dialog> dialog = dialogRepository.findRandomDialog();

        if (dialog.isPresent()) {
            GameSession newSession = new GameSession(dialog.get().getId(), 5);
            gameSessions.put(username, newSession); // Add session to map
            System.out.println("Added new game session for user: '" + username + "' with Dialog ID: " + dialog.get().getId()); // Log session creation
            return "Guess the movie for this dialogue: \"" + dialog.get().getDialogueText() + "\"";
        }
        System.out.println("No active dialog found to start game."); // Log failure
        return "No active dialog found";
    }

    public String verifyGuess(String username,String guess) {
        int currentScore = getUserScore(username);
        GameSession session=gameSessions.get(username);

        if (session==null) {
            return "You are not in a game";
        }
        if (session.getAttemptsLeft()<=0){
            gameSessions.remove(username);
            return "You are out of guesses";
        }

        Optional<Dialog> dialog=dialogRepository.findById(session.getDialogId());
        if (dialog.isPresent()) {
            Long mID=dialog.get().getMovie().getId();

            Optional<String> movie=movieRepository.findTitleByMovieId(mID);
            if (movie.isPresent() && movie.get().equalsIgnoreCase(guess)) {
                 currentScore+=1;
                setUserScore(username,currentScore);
                gameSessions.remove(username);
                return "Correct guess " + username + " Your score is : " + getUserScore(username) ;
            }
            else {
                session.decrementAttempts();
                return "Incorrect guess , Remaining attempts left : " +session.getAttemptsLeft();
            }
        }
    return "Error fetching movie details.";
    }

    public int getUserScore(String username){
        Optional<User> userOptional = userRepo.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();
        return user.getScore();
    }

    public void setUserScore(String username,int score){
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }
        User user = userOptional.get();
        user.setScore(score);
        userRepo.save(user);
    }

    public String restartGame( String username) {
        System.out.println("Attempting to restart game for user: '" + username + "'"); // Log entry
        boolean sessionExists = gameSessions.containsKey(username);
        System.out.println("Session exists for user? " + sessionExists); // Log the result of the check

        if (!sessionExists) {
            System.out.println("Returning 'You are not in a game' because session doesn't exist."); // Log why
            return "You are not in a game";
        }

        System.out.println("Removing game session for user: '" + username + "'"); // Log before remove
        gameSessions.remove(username);
        System.out.println("Session removed."); // Log after remove
        return "Game restarted successfully!"; // Clearer success message
    }
}
