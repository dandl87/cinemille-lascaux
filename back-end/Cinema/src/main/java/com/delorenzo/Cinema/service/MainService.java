package com.delorenzo.Cinema.service;

import com.delorenzo.Cinema.dto.MovieScreening;
import com.delorenzo.Cinema.dto.NewMovie;
import com.delorenzo.Cinema.entity.Movie;
import com.delorenzo.Cinema.entity.Week;
import com.delorenzo.Cinema.exception.DataRetrievingFromExcelException;
import com.delorenzo.Cinema.logic.Scheduler;
import com.delorenzo.Cinema.repository.MovieRepository;
import com.delorenzo.Cinema.entity.Room;
import com.delorenzo.Cinema.repository.RoomRepository;
import com.delorenzo.Cinema.entity.Screening;
import com.delorenzo.Cinema.repository.ScreeningRepository;
import com.delorenzo.Cinema.repository.WeekRepository;
import com.delorenzo.Cinema.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);
    private final MovieRepository movieRepository;
    private final MoviesFromExcelService moviesFromExcel;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final WeekRepository weekRepository;
    private final Scheduler imaxScheduler;
    private final Scheduler regularScheduler;

    public MainService(
            MovieRepository movieRepository,
            MoviesFromExcelService moviesFromExcel,
            RoomRepository roomRepository,
            ScreeningRepository screeningRepository,
            WeekRepository weekRepository,
            Scheduler imaxScheduler,
            Scheduler regularScheduler) {
        this.movieRepository = movieRepository;
        this.moviesFromExcel = moviesFromExcel;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
        this.weekRepository = weekRepository;
        this.imaxScheduler = imaxScheduler;
        this.regularScheduler = regularScheduler;
    }


    //  second scheduling process with screenings/week saving ( from movies in file excel )
    public void weeklyBatch() throws DataRetrievingFromExcelException {

        try {

            List<NewMovie> newMovies = moviesFromExcel.readFile();

            List<Movie> regularMovies = new ArrayList<>();
            List<Movie> imaxMovies = new ArrayList<>();

            for (NewMovie newMovie : newMovies) {
                Movie movie = saveMovie(newMovie);
                if (movie.isImax())
                    imaxMovies.add(movie);
                else
                    regularMovies.add(movie);
            }

            imaxScheduler.scheduling(imaxMovies);

            regularScheduler.scheduling(regularMovies);

            List<Scheduler> schedulers = new ArrayList<>();
            schedulers.add(imaxScheduler);
            schedulers.add(regularScheduler);

            Week week = Utils.buildAWeek(schedulers, false);

            weekRepository.save(week);

        } catch (IOException e) {
            throw new DataRetrievingFromExcelException();
        }

    }

    //  first scheduling process with screenings/week saving ( from movies in db )
    public void initializationBatch() {

            List<Movie> newMovies =  movieRepository.findAll();

            List<Movie> regularMovies = new ArrayList<>();
            List<Movie> imaxMovies = new ArrayList<>();

            for (Movie movie : newMovies) {
                if (movie.isImax())
                    imaxMovies.add(movie);
                else
                    regularMovies.add(movie);
            }

            imaxScheduler.scheduling(imaxMovies);

            regularScheduler.scheduling(regularMovies);

            List<Scheduler> schedulers = new ArrayList<>();
            schedulers.add(imaxScheduler);
            schedulers.add(regularScheduler);

            Week week = Utils.buildAWeek(schedulers, true);

            weekRepository.save(week);

    }

    public List<Movie> findMovie(Movie movie) {
        Example<Movie> example = Example.of(movie);
        return movieRepository.findAll(example);
    }

    public List<Movie> findMovieTitledLike(String title) {
        Movie movie = new Movie();
        movie.setTitle(title);
        ExampleMatcher matcher = matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues()
                .withMatcher("title", match -> match.contains());
        Example<Movie> example = Example.of(movie, matcher);
        return movieRepository.findAll(example);
    }

    public Optional<Movie> findMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }


    public Set<MovieScreening> findMovieScreeningsOfTheWeek(String day) {
        final LocalDate daySearched = LocalDate.parse(day);
        LocalDate mondayFounded = Utils.findTheMondayOfTheWeek(daySearched);
        Optional<Week> week = weekRepository.findByMonday(mondayFounded);
        if (week.isPresent()) {
            Set<Screening> screenings = week.get().getScreenings();
            Optional<Set<MovieScreening>> movieScreenings = Utils.createMovieScreeningsFromScreenings(screenings);
            return movieScreenings.orElse(new HashSet<>());
        }
        return new HashSet<>();
    }
    public Set<MovieScreening> findMovieScreeningsOfTheLastWeek() {
        Week week = weekRepository.findFirstByOrderByMondayDesc();
        Set<Screening> screenings = week.getScreenings();
            Optional<Set<MovieScreening>> movieScreenings = Utils.createMovieScreeningsFromScreenings(screenings);
            return movieScreenings.orElse(new HashSet<>());

    }

    private Movie createAMovie(NewMovie newMovie) {
        Movie movie = new Movie();
        movie.setTitle(newMovie.getTitle());
        movie.setDirector(newMovie.getDirector());
        movie.setYear(String.valueOf(newMovie.getYear()));
        movie.setDuration(newMovie.getDuration());
        movie.setImax(newMovie.isImax());
        movie.setValue(newMovie.getValue());

        return movie;
    }

    private Movie saveMovie(NewMovie newMovie) {

        Movie movie = createAMovie(newMovie);
        movieRepository.save(movie);
        logger.info("Movie {} created", movie.getTitle());
        return movie;

    }


}
