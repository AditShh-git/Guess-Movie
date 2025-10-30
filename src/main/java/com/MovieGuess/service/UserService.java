package com.MovieGuess.service;

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

    public void saveUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User with name " + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
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

    public void updateScore(int score, String username) {
        User user = findByUsername(username);
        user.setScore(user.getScore() + score);
        userRepo.save(user);
    }

    public int getScoreByUsername(String username){
        return findByUsername(username).getScore();
    }
}
