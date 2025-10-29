package com.MovieGuess.repo;

import com.MovieGuess.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Long> {
//    @Query(value = "SELECT * FROM movies ORDER BY RAND() LIMIT 1", nativeQuery = true)
//    Optional<Movie> findRandomMovie();

    @Query("SELECT m.industry FROM Movie m WHERE m.id = :movieId")
    Optional<String> findIndustryByMovieId(Long movieId);

    @Query("SELECT m.releaseYear FROM Movie m WHERE m.id = :movieId")
    Optional<Integer> findReleaseYearByMovieId(Long movieId);

    @Query("SELECT m.title FROM Movie m WHERE m.id = :movieId")
    Optional<String> findTitleByMovieId(Long movieId);

    @Query("SELECT m.title FROM Movie m")
    List<String> findAllMoviesTitles();
}
