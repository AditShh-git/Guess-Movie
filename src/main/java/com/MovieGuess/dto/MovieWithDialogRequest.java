package com.MovieGuess.dto;

import com.MovieGuess.model.Dialog;
import lombok.Data;

import java.util.List;

@Data
public class MovieWithDialogRequest {
    private String title;
    private String industry;
    private int releaseYear;
    private List<Dialog> dialogs;
}
