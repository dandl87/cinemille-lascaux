package com.delorenzo.Cinema.utils;

import com.delorenzo.Cinema.dto.MovieScreening;
import com.delorenzo.Cinema.entity.Movie;
import com.delorenzo.Cinema.entity.Room;
import com.delorenzo.Cinema.entity.Screening;
import com.delorenzo.Cinema.entity.Week;
import com.delorenzo.Cinema.logic.Scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Utils {

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Screening createAScreening(Movie movie){
        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setNumberOfWeeks(0);
        return screening;
    }

    public static Set<Screening> createScreenings(List<Movie> movies){
        Set<Screening> screenings = new HashSet<>();
        for(Movie movie : movies){
        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setNumberOfWeeks(0);
        screenings.add(screening);
        }
        return screenings;
    }

    public static Optional<Set<MovieScreening>> createMovieScreeningsFromScreenings(Set<Screening> screenings){
        Set<MovieScreening> movieScreenings = new HashSet<>();
        for (Screening screening : screenings) {
            Movie movie = screening.getMovie();
            Room room = screening.getRoom();
            MovieScreening movieScreening = new MovieScreening();
            movieScreening.setId(screening.getId());
            movieScreening.setRoom(room.getName());
            movieScreening.setTitle(movie.getTitle());
            movieScreening.setDirector(movie.getDirector());
            movieScreening.setYear(movie.getYear());
            movieScreening.setDuration(movie.getDuration());

            movieScreening.setSeatsAvailable(room.getSeats());
            movieScreenings.add(movieScreening);
        }
        if(!movieScreenings.isEmpty()){
            return Optional.of(movieScreenings);
        }else {
            return Optional.empty();
        }
    }

    public static Week buildAWeek(List<Scheduler> schedulers, boolean aWeekAgo) {
        Week week = new Week();
        Set<Screening> screenings = new HashSet<>();
        for (Scheduler scheduler : schedulers) {
            List<Screening> screeningsPartial = scheduler.getScheduledScreenings();
            for (Screening screening : screeningsPartial) {
                if (screening != null)
                    screenings.add(screening.clone());
            }
        }
        week.setScreenings(screenings);
        for (Screening screening : screenings) {
            screening.setWeek(week);
            screening.setNumberOfWeeks(screening.getNumberOfWeeks() + 1);
        }
        if (aWeekAgo) {
            int year = 2025;
            int month = 3;
            int day = 31;
            LocalDate pastMonday = LocalDate.of(year, month, day);
            week.setMonday(pastMonday);
        } else {
            int year = 2025;
            int month = 4;
            int day = 7;
            LocalDate monday = LocalDate.of(year, month, day);
            week.setMonday(monday);
        }
        return week;
    }

    public static Screening extractScreeningWithMaxValue(Set<Screening> screenings){
        Screening screeningWithMaxValue = new Screening();
        double maxValue=0;
        for (Screening screening : screenings) {
            if(screening.getMovie().getValue() >maxValue){
                maxValue = screening.getMovie().getValue();
                screeningWithMaxValue = screening;
            }
        }
        return screeningWithMaxValue;

    }

    public static LocalDate findTheMondayOfTheWeek(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        LocalDate result = switch (day) {
            case TUESDAY -> date.minusDays(1);
            case WEDNESDAY -> date.minusDays(2);
            case THURSDAY -> date.minusDays(3);
            case FRIDAY -> date.minusDays(4);
            case SATURDAY -> date.minusDays(5);
            case SUNDAY -> date.minusDays(6);
            default -> date;
        };
        return result;
    }
}
