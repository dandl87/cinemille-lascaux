package com.delorenzo.Cinema.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer seats;
    private Boolean imax;

    @OneToMany(mappedBy = "room",
                cascade = CascadeType.PERSIST,
                orphanRemoval = false)
    private Set<Screening> screenings = new HashSet<>();

    public Room() {}

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Boolean getImax() {
        return imax;
    }

    public void setImax(Boolean imax) {
        this.imax = imax;
    }


}
