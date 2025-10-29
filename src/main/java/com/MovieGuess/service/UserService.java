package com.MovieGuess.service;

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
    
    public void saveUser(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()){
            throw  new IllegalStateException("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }



}
