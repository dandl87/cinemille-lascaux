package com.delorenzo.Cinema.controller;

import com.delorenzo.Cinema.dto.MovieScreening;
import com.delorenzo.Cinema.entity.Movie;
import com.delorenzo.Cinema.entity.Room;
import com.delorenzo.Cinema.service.MainService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class MainController {


    private final MainService mainService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }


    @GetMapping("/movie-screenings/week")
    public ResponseEntity<Set<MovieScreening>> findMovieScreeningsOfAWeek(@RequestParam String day) {
        logger.info("movies of {}", day);
        Set<MovieScreening> movieScreenings = mainService.findMovieScreeningsOfTheWeek(day);
        return ResponseEntity.ok(movieScreenings);
    }

    @GetMapping("/movie-screenings/last-week")
    public ResponseEntity<Set<MovieScreening>> findMovieScreeningsOfTheLastWeek( ) {
        Set<MovieScreening> movieScreenings = mainService.findMovieScreeningsOfTheLastWeek();
        return ResponseEntity.ok(movieScreenings);
    }


    // extra

    @PostMapping("/movies/find-by-example")
    public List<Movie> findMovie(
            @RequestBody @Valid Movie movie) {
        return mainService.findMovie(movie);
    }

    @GetMapping("/movies/find-by-title")
    public Optional<Movie> findMovie(
            @RequestParam String title) {
        return mainService.findMovieByTitle(title);
    }


    @GetMapping("/movies/search")
    public ResponseEntity<List<Movie>> searchMovie(
            @RequestParam String title) {
        List<Movie> movies = mainService.findMovieTitledLike(title);
        return ResponseEntity.ok(movies);
    }




}
