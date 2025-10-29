package com.MovieGuess.controller;

import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/game/hints")
public class HintController {

    @Autowired
    private MovieRepo movieRepository;

    @Autowired
    private DialogRepo dialogRepository;

    // Hint 1: Get the industry based on dialogue
    @GetMapping("/hint1")
    public String getHint1(@RequestParam String dialogueText) {
        Optional<String> industry = dialogRepository.findIndustryByDialogue(dialogueText);
        return industry.map(s -> "Industry: " + s).orElse("Industry not found.");
    }

    // Hint 2: Get the release year based on dialogue
    @GetMapping("/hint2")
    public String getHint2(@RequestParam String dialogueText) {
        Optional<Integer> releaseYear = dialogRepository.findReleaseYearByDialogue(dialogueText);
        return releaseYear.map(integer -> "Release Year: " + integer).orElse("Release year not found.");
    }

    // Hint 3: Get the actor who delivered the dialogue
    @GetMapping("/hint3")
    public String getHint3(@RequestParam String dialogueText) {
        Optional<String> actor = dialogRepository.findActorByDialogue(dialogueText);
        return actor.map(s -> "Lead actor: " + s).orElse("Actor not found.");
    }
}
