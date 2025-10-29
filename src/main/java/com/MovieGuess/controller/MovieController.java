package com.MovieGuess.controller;

import com.MovieGuess.dto.MovieWithDialogRequest;
import com.MovieGuess.model.Movie;
import com.MovieGuess.repo.MovieRepo;
import com.MovieGuess.service.MovieDialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepo movieRepository;

    @Autowired
    private MovieDialogService movieDialogService;

//    @Autowired
//    private MovieWithDialogRequest movieWithDialogRequest;

    // Post mapping for adding new movie details (Only admin can access this)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addMovie(@RequestBody Movie movie) {
        movieRepository.save(movie);
        return "Movie added successfully! Movie id: " + movie.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addWithDialogs")
    public ResponseEntity<String> addMovieWithDialogs(@RequestBody MovieWithDialogRequest movieWithDialogsDTO) {
        movieDialogService.saveMovieAndDialogs(movieWithDialogsDTO);
        return ResponseEntity.ok("Movie and Dialogs added successfully!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAllMovies());
    }
}