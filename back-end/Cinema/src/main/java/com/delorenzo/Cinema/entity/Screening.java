package com.delorenzo.Cinema.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "screenings")
public class Screening implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "week_id")
    private Week week;

    private int numberOfWeeks;


    public Screening() {
    }

    public Screening(Room room, Movie movie,Week week) {
        this.room = room;
        this.movie = movie;
        this.week = week;
    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }


    @Override
    public Screening clone() {
        Screening s = new Screening();
        s.setRoom(room);
        s.setMovie(movie);
        s.setWeek(week);
        s.setNumberOfWeeks(numberOfWeeks);
        return s;
    }
}
