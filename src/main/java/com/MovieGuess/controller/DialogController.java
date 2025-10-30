package com.MovieGuess.controller;

import com.MovieGuess.dto.DialogueDto;
import com.MovieGuess.service.MovieDialogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {

   private final MovieDialogService movieDialogService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addDialog(@Valid @RequestBody DialogueDto dialogDto, @RequestParam Long movieId) {
        try {
            movieDialogService.saveSingleDialog(dialogDto, movieId);
            return ResponseEntity.ok("Dialog added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding dialog: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{dialogId}")
    public ResponseEntity<String> deleteDialogue(@PathVariable Long dialogId) {
        try {
            movieDialogService.deleteDialog(dialogId);
            return ResponseEntity.ok("Dialogue with ID " + dialogId + " deleted successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting dialogue: " + e.getMessage());
        }
    }
}
