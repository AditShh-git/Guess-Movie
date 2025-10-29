package com.MovieGuess.controller;

import com.MovieGuess.dto.DialogDto;
import com.MovieGuess.model.Dialog;
import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.service.MovieDialogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {


    private final MovieDialogService movieDialogService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Use hasAuthority for consistency
    @PostMapping("/add")
    // Accept DialogDto and the required movieId
    public ResponseEntity<String> addDialog(@RequestBody DialogDto dialogDto, @RequestParam Long movieId) {
        try {
            // We need a new service method to handle this
            movieDialogService.saveSingleDialog(dialogDto, movieId);
            return ResponseEntity.ok("Dialog added successfully!");
        } catch (Exception e) {
            // Handle potential errors like movie not found
            return ResponseEntity.badRequest().body("Error adding dialog: " + e.getMessage());
        }
    }
}
