package com.MovieGuess.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    private String industry;

    private Integer releaseYear;

//    private String leadActor;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Dialog> dialogs = new ArrayList<>();

}
