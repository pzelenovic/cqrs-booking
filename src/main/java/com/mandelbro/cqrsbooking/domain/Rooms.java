package com.mandelbro.cqrsbooking.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rooms {

    private final List<Room> rooms = new ArrayList<>();

    public void addAll(Room... newRooms) {
        Arrays.stream(newRooms).forEach(this::add);
    }

    public void add(Room room) {
        this.rooms.add(room);
    }

    public int totalNumber() {
        return this.rooms.size();
    }

    public Rooms findVacantDuringPeriod(BookingPeriod bookingPeriod) {
        Rooms vacantRooms = new Rooms();
        this.rooms.forEach(room -> {
            if (room.isVacant(bookingPeriod)) {
                vacantRooms.add(room);
            }
        });
        return vacantRooms;
    }
}
