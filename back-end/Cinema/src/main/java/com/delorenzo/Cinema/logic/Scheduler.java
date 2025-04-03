package com.delorenzo.Cinema.logic;

import com.delorenzo.Cinema.entity.Movie;
import com.delorenzo.Cinema.entity.Screening;
import com.delorenzo.Cinema.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scheduler {

    private final Slot[] slots;
    private final int numberOfRooms;
    private int freeRooms;

    public Scheduler(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        this.freeRooms = numberOfRooms;
        this.slots = new Slot[numberOfRooms];

    }


    public int getNumberOfRooms() {
        return numberOfRooms;
    }


    public Slot[] getSlots() {
        return slots;
    }

    public void scheduling(List<Movie> movies) {
        removeScreenings(3);
        if (freeRooms >= movies.size()) {
            Set<Screening> screenings = Utils.createScreenings(movies);
            assignToEmptySlot(screenings);
        }else assignByValue(movies);

    }

    // screening assigned to empty slot and for the others week value incremented
    //logic: se lo slot è vuoto inserisco il nuovo screening ( la proiezione di film ) nello slot
    // se slot è pieno aggiungo una settimana di proiezione e passo allo slot successivo
    private void assignToEmptySlot(Set<Screening> screenings){
        int j = 0;
        for(Screening screening : screenings){
            if(slots[j].getScreening() == null){
                screening.setRoom(slots[j].getRoom());
                slots[j].setScreening(screening);
                j++;
                freeRooms--;
            } else {
                int weeks = slots[j].getScreening().getNumberOfWeeks();
                slots[j].getScreening().setNumberOfWeeks(weeks + 1);
                j++;

            }
        }
    }


        //  la logica di riassegnazione del film per valore
    private void assignByValue(List<Movie> movies) {
        Set<Screening> newScreenings = Utils.createScreenings(movies);
        for (int k = 0; k < numberOfRooms; k++) {
            if (slots[k].getScreening() == null) {
                Screening screening = Utils.extractScreeningWithMaxValue(newScreenings);
                screening.setRoom(slots[k].getRoom());
                slots[k].setScreening(screening);
                newScreenings.remove(screening);
                freeRooms--;
            } else {
                newScreenings.add(slots[k].getScreening());
                Screening screening = Utils.extractScreeningWithMaxValue(newScreenings);
                screening.setRoom(slots[k].getRoom());
                slots[k].setScreening(screening);
                newScreenings.remove(slots[k].getScreening());
                freeRooms--;
            }
        }



    }


    private void removeScreenings ( int weeks){
        for (int i = 0; i < numberOfRooms; i++) {
            if ((slots[i].getScreening() != null ) && (slots[i].getScreening().getNumberOfWeeks() == weeks)) {
                slots[i].setScreening(null);
                freeRooms++;
            }
        }
    }

    public List<Screening> getScheduledScreenings() {
        List<Screening> screenings = new ArrayList<>();
        for (Slot slot : slots) {
            screenings.add(slot.getScreening());
        }
        return screenings;
    }


}