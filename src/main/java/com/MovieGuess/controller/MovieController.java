package com.MovieGuess.controller;

import com.MovieGuess.dto.MovieDto;
import com.MovieGuess.dto.MovieWithDialogRequest;
import com.MovieGuess.model.Movie;
import com.MovieGuess.repo.MovieRepo;
import com.MovieGuess.service.MovieDialogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepo movieRepository;
    private final MovieDialogService movieDialogService;

//    @Autowired
//    private MovieWithDialogRequest movieWithDialogRequest;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@Valid @RequestBody MovieDto movieDto) { // <-- Use MovieDto
        try {
            movieDialogService.saveMovie(movieDto);
            return ResponseEntity.ok("Movie added successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding movie: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addWithDialogs")
    public ResponseEntity<String> addMovieWithDialogs(@Valid @RequestBody MovieWithDialogRequest movieWithDialogsDTO) {
        movieDialogService.saveMovieAndDialogs(movieWithDialogsDTO);
        return ResponseEntity.ok("Movie and Dialogs added successfully!");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all-titles")
    public ResponseEntity<List<String>> getAllMovieTitles() {
        return ResponseEntity.ok(movieRepository.findAllMoviesTitles());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId) {
        try {
            movieDialogService.deleteMovie(movieId);
            return ResponseEntity.ok("Movie with ID " + movieId + " deleted successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting movie: " + e.getMessage());
        }
    }
}