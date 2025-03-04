package com.MovieGuess.controller;

import com.MovieGuess.model.Dialog;
import com.MovieGuess.repo.DialogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dialogs")
public class DialogController {

    @Autowired
    private DialogRepo dialogRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addDialog(@RequestBody Dialog dialog) {
        dialogRepository.save(dialog);
        return "Dialog added successfully!";
    }
}
