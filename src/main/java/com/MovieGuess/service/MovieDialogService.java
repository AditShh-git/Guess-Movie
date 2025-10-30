package com.MovieGuess.service;

import com.MovieGuess.dto.DialogueDto;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieDialogService {

   private final MovieRepo movieRepo;
   private final DialogRepo dialogRepo;

    @Transactional
    public void saveMovieAndDialogs(MovieWithDialogRequest dto){
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setIndustry(dto.getIndustry());
        movie.setReleaseYear(dto.getReleaseYear());

        List<DialogueDto> dialogDtos = dto.getDialogs();
        if (dialogDtos != null && !dialogDtos.isEmpty()) {
            List<Dialog> dialogEntities = dialogDtos.stream().map(dialogDto -> {
                Dialog dialog = new Dialog();
                dialog.setDialogueText(dialogDto.getDialogueText());
                dialog.setDialogueActor(dialogDto.getDialogueActor());
                dialog.setMovie(movie); // Set relationship
                return dialog;
            }).collect(Collectors.toList());
            movie.setDialogs(dialogEntities);
        }

        movieRepo.save(movie); // Cascade saves dialogs
    }

    @Transactional
    public void saveSingleDialog(DialogueDto dialogDto, Long movieId) {

        Movie movie = movieRepo.findById(movieId)
                                .orElseThrow(() -> new EntityNotFoundException("Movie not found with ID: " + movieId));


        Dialog dialog = new Dialog();
        dialog.setDialogueText(dialogDto.getDialogueText());
        dialog.setDialogueActor(dialogDto.getDialogueActor());
        dialog.setMovie(movie); // Set the relationship


        dialogRepo.save(dialog);
    }
}
