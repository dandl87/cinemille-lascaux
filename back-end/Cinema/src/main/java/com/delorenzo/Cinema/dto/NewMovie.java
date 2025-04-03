package com.delorenzo.Cinema.dto;

import java.util.Date;

public class NewMovie {
    private String title;
    private String director;
    private int year;
    private int duration;
    private boolean imax;
    private double value;
    private Date out;
    private String room;

    public NewMovie() {

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getOut() {
        return out;
    }

    public void setOut(Date out) {
        this.out = out;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "NewMovie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", imax=" + imax +
                ", value=" + value +
                ", out=" + out +
                ", room='" + room + '\'' +
                '}';
    }
}
