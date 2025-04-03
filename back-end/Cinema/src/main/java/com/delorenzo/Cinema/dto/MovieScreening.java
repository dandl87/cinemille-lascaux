package com.delorenzo.Cinema.dto;

import java.time.LocalDate;

public class MovieScreening {
    private long id;
    private String title;
    private String director;
    private String year;
    private Integer duration;
    private String room;
    private Integer seatsAvailable;
    private LocalDate dateOfStart;
    private LocalDate dateOfEnd;

    public MovieScreening() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public LocalDate getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(LocalDate dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public LocalDate getDateOfEnd() {
        return dateOfEnd;
    }

    public void setDateOfEnd(LocalDate dateOfEnd) {
        this.dateOfEnd = dateOfEnd;
    }

    @Override
    public String toString() {
        return "MovieScreening{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year='" + year + '\'' +
                ", duration=" + duration +
                ", room='" + room + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", dateOfStart=" + dateOfStart +
                ", dateOfEnd=" + dateOfEnd +
                '}';
    }
}
