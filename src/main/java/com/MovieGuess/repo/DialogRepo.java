package com.MovieGuess.repo;

import com.MovieGuess.model.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DialogRepo extends JpaRepository<Dialog,Long> {
    // Query for fetching a random dialogue is fine
    @Query("SELECT d FROM Dialog d ORDER BY FUNCTION('RAND') LIMIT 1")
    Optional<Dialog> findRandomDialog();


    // Query for last shown dialog is fine
    @Query("SELECT d FROM Dialog d ORDER BY d.id DESC LIMIT 1")
    Optional<Dialog> findLastShownDialog();

    // Queries linking to Movie details are fine
    @Query("SELECT m.industry FROM Movie m JOIN Dialog d ON d.movie.id = m.id WHERE d.dialogueText = :dialogueText")
    Optional<String> findIndustryByDialogue(String dialogueText);

    @Query("SELECT m.releaseYear FROM Movie m JOIN Dialog d ON d.movie.id = m.id WHERE d.dialogueText = :dialogueText")
    Optional<Integer> findReleaseYearByDialogue(String dialogueText);

    @Query("SELECT d.dialogueActor FROM Dialog d WHERE d.dialogueText = :dialogueText")
    Optional<String> findActorByDialogue(String dialogueText);
}
