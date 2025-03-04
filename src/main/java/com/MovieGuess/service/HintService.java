package com.MovieGuess.service;

import com.MovieGuess.model.Dialog;
import com.MovieGuess.model.Movie;
import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
//@RequiredArgsConstructor
public class HintService {


    @Autowired
    private MovieRepo movieRepository;

    @Autowired
    private DialogRepo dialogRepository;

    private Random random = new Random();

    // Fetch industry based on movieId
    public String getHint1(Long movieId) {
        return movieRepository.findIndustryByMovieId(movieId)
                .orElse("Industry not found.");
    }

    // Fetch release year based on movieId
    public String getHint2(Long movieId) {
        return movieRepository.findReleaseYearByMovieId(movieId)
                .map(year -> "Released in: " + year)
                .orElse("Release year not found.");
    }

    // Fetch the actor who delivered the shown dialogue
    public String getHint3(String dialogueText) {
        return dialogRepository.findActorByDialogue(dialogueText)
                .map(actor -> "Lead actor: " + actor)
                .orElse("Actor not found.");
    }

}
