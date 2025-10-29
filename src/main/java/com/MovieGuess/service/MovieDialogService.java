package com.MovieGuess.service;

import com.MovieGuess.dto.DialogDto;
import com.MovieGuess.dto.MovieWithDialogRequest;
import com.MovieGuess.model.Dialog;
import com.MovieGuess.model.Movie;
import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieDialogService {


    private final MovieRepo movieRepository;
    private final DialogRepo dialogRepository;


    @Transactional
    public void saveMovieAndDialogs(MovieWithDialogRequest dto){ // Renamed for clarity
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setIndustry(dto.getIndustry());
        movie.setReleaseYear(dto.getReleaseYear());

        // --- IMPORTANT: MAP DTOs TO ENTITIES ---
        List<DialogDto> dialogDtos = dto.getDialogs();
        if (dialogDtos != null && !dialogDtos.isEmpty()) {
            List<Dialog> dialogEntities = dialogDtos.stream().map(dialogDto -> {
                Dialog dialog = new Dialog();
                dialog.setDialogueText(dialogDto.getDialogueText());
                dialog.setDialogueActor(dialogDto.getDialogueActor());
                dialog.setMovie(movie); // Set relationship here
                return dialog;
            }).toList();
            movie.setDialogs(dialogEntities);
        }
        // --- END MAPPING ---

        movieRepository.save(movie); // Cascade will save dialogs
    }

    // --- ADD THIS NEW METHOD ---
    @Transactional
    public void saveSingleDialog(DialogDto dialogDto, Long movieId) {
        // 1. Find the Movie the dialog belongs to
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with ID: " + movieId));

        // 2. Create a Dialog entity from the DTO
        Dialog dialog = new Dialog();
        dialog.setDialogueText(dialogDto.getDialogueText());
        dialog.setDialogueActor(dialogDto.getDialogueActor());
        dialog.setMovie(movie); // Set the relationship

        // 3. Save the Dialog entity
        dialogRepository.save(dialog);
    }
}
