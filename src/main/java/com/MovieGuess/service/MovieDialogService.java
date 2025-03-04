package com.MovieGuess.service;

import com.MovieGuess.dto.MovieWithDialogRequest;
import com.MovieGuess.model.Dialog;
import com.MovieGuess.model.Movie;
import com.MovieGuess.repo.DialogRepo;
import com.MovieGuess.repo.MovieRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDialogService {

    @Autowired
    private MovieRepo movieRepository;

    @Autowired
    private DialogRepo dialogRepository;

    @Transactional

public void saveMovieandDialog(MovieWithDialogRequest dto){
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setIndustry(dto.getIndustry());
        movie.setReleaseYear(dto.getReleaseYear());

        List<Dialog> dialogs = dto.getDialogs();
        if (dialogs != null) {
            for (Dialog dialog : dialogs) {
                dialog.setMovie(movie);
                dialog.setDialogueActor(dialog.getDialogueActor());
            }
            movie.setDialogs(dialogs);
        }

        movieRepository.save(movie);
    }
}
