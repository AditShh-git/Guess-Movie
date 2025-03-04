package com.MovieGuess.repo;

import com.MovieGuess.model.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DialogRepo extends JpaRepository<Dialog,Long> {
//    @Query("SELECT d.movie.leadActor FROM Dialog d WHERE d.id = :dialogId")
//    Optional<String> findLeadActorByDialogId(Long dialogId);
//
//    Optional<Dialog> findRandomDialogByMovieId(Long movieId);

    @Query("SELECT d FROM Dialog d ORDER BY FUNCTION('RAND') LIMIT 1")
    Optional<Dialog> findRandomDialog();


    // Get the last shown dialog (Assume single player per session for simplicity)
    @Query("SELECT d FROM Dialog d ORDER BY d.id DESC LIMIT 1")
    Optional<Dialog> findLastShownDialog();

    @Query("SELECT m.industry FROM Movie m JOIN Dialog d ON d.movie.id = m.id WHERE d.dialogueText = :dialogueText")
    Optional<String> findIndustryByDialogue(String dialogueText);

    // Find the release year based on the dialogue text
    @Query("SELECT m.releaseYear FROM Movie m JOIN Dialog d ON d.movie.id = m.id WHERE d.dialogueText = :dialogueText")
    Optional<Integer> findReleaseYearByDialogue(String dialogueText);

    // Find the actor who delivered the dialogue based on the dialogue text
    @Query("SELECT d.dialogueActor FROM Dialog d WHERE d.dialogueText = :dialogueText")
    Optional<String> findActorByDialogue(String dialogueText);
}
