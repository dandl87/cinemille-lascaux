package com.delorenzo.Cinema.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String director;
    private String year;
    private Integer duration;
    private boolean imax;
    private double value;

    @OneToMany(mappedBy = "movie",
                cascade = CascadeType.PERSIST,
                orphanRemoval = false)
    private Set<Screening> screenings = new HashSet<>();


    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    public boolean isImax() {
        return imax;
    }

    public void setImax(boolean imax) {
        this.imax = imax;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


}
