package com.MovieGuess.service;

import com.MovieGuess.dto.DialogueDto;
import com.MovieGuess.dto.MovieDto;
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
                dialog.setMovie(movie);
                return dialog;
            }).collect(Collectors.toList());
            movie.setDialogs(dialogEntities);
        }

        movieRepo.save(movie);
    }

    @Transactional
    public void saveSingleDialog(DialogueDto dialogDto, Long movieId) {

        Movie movie = movieRepo.findById(movieId)
                                .orElseThrow(() -> new EntityNotFoundException("Movie not found with ID: " + movieId));


        Dialog dialog = new Dialog();
        dialog.setDialogueText(dialogDto.getDialogueText());
        dialog.setDialogueActor(dialogDto.getDialogueActor());
        dialog.setMovie(movie);


        dialogRepo.save(dialog);
    }

    @Transactional
    public void saveMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setIndustry(movieDto.getIndustry());
        movie.setReleaseYear(movieDto.getReleaseYear());

        movieRepo.save(movie);
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        if (!movieRepo.existsById(movieId)) {
            throw new EntityNotFoundException("Movie not found with ID: " + movieId);
        }

        movieRepo.deleteById(movieId);
    }

    @Transactional
    public void deleteDialog(Long dialogId) {
        if (!dialogRepo.existsById(dialogId)) {
            throw new EntityNotFoundException("Dialogue not found with ID: " + dialogId);
        }
        dialogRepo.deleteById(dialogId);
    }

}
