package com.MovieGuess.service;

import com.MovieGuess.dto.RegisterRequest;
import com.MovieGuess.dto.UserDto;
import com.MovieGuess.model.Role;
import com.MovieGuess.model.User;
import com.MovieGuess.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getScore(), user.getRole());
    }

    public void saveUser(RegisterRequest registerRequest) {
        if (userRepo.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new IllegalStateException("Username is already taken");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        user.setScore(0);

        userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
    }

    public UserDto getUserDtoByUsername(String username) {
        User user = findByUsername(username);
        return mapToUserDto(user);
    }

    public void updateScore(String username, int points) {
        User user = findByUsername(username);
        user.setScore(user.getScore() + points);
        userRepo.save(user);
    }

    public int getScoreByUsername(String username){
        return findByUsername(username).getScore();
    }
}
