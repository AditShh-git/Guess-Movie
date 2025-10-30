package com.MovieGuess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Industry is required")
    private String industry;


    @Min(value = 1888, message = "Release year must be a valid year")
    private int releaseYear;

}
