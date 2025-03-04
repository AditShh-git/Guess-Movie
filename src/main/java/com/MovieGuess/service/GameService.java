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
        if (gameSessions.containsKey(username)) {
            return "You already have an active game! Use your remaining attempts.";
        }

        Optional<Dialog> dialog=dialogRepository.findRandomDialog();

        if (dialog.isPresent()) {
            gameSessions.put(username,new GameSession(dialog.get().getId(),5));
            return "Guess the movie for this dialogue: \"" + dialog.get().getDialogueText() + "\"";
        }
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
        if (!gameSessions.containsKey(username)) {
            return "You are not in a game";
        }
            gameSessions.remove(username);
        return "Restarting the game";
    }
}
