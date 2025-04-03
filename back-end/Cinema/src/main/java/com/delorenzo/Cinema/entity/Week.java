package com.delorenzo.Cinema.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "weeks")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Temporal(TemporalType.DATE)
    private LocalDate monday;


    @OneToMany(mappedBy = "week",
            cascade = CascadeType.PERSIST,
            orphanRemoval = false)
    private Set<Screening> screenings = new HashSet<>();



    public Long getId() {
        return id;
    }

    public Week() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMonday() {
        return monday;
    }

    public void setMonday(LocalDate monday) {
        this.monday = monday;
    }


    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }
}
