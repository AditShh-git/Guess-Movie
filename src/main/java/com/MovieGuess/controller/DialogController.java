package com.MovieGuess.controller;

import com.MovieGuess.dto.DialogueDto;
import com.MovieGuess.service.MovieDialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {

   private final MovieDialogService movieDialogService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addDialog(@RequestBody DialogueDto dialogDto, @RequestParam Long movieId) {
        try {
            movieDialogService.saveSingleDialog(dialogDto, movieId);
            return ResponseEntity.ok("Dialog added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding dialog: " + e.getMessage());
        }
    }
}
