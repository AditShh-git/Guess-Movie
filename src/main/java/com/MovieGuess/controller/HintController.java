package com.MovieGuess.controller;

import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/game/hints")
@RequiredArgsConstructor
public class HintController {


    private final MovieRepo movieRepository;
    private final DialogRepo dialogRepository;


    @GetMapping("/hint1")
    public String getHint1(@RequestParam String dialogueText) {
        Optional<String> industry = dialogRepository.findIndustryByDialogue(dialogueText);
        return industry.map(s -> "Industry: " + s).orElse("Industry not found.");
    }


    @GetMapping("/hint2")
    public String getHint2(@RequestParam String dialogueText) {
        Optional<Integer> releaseYear = dialogRepository.findReleaseYearByDialogue(dialogueText);
        return releaseYear.map(integer -> "Release Year: " + integer).orElse("Release year not found.");
    }


    @GetMapping("/hint3")
    public String getHint3(@RequestParam String dialogueText) {
        Optional<String> actor = dialogRepository.findActorByDialogue(dialogueText);
        return actor.map(s -> "Lead actor: " + s).orElse("Actor not found.");
    }
}
