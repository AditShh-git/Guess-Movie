package com.MovieGuess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DialogueDto {

    @NotBlank(message = "Dialogue text is required")
    private String dialogueText;

    @NotBlank(message = "Dialogue actor is required")
    private String dialogueActor;

}
