package com.mandelbro.cqrsbooking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bookings {

    private final List<Booking> bookings = new ArrayList<>();

    public int size() {
        return this.bookings.size();
    }

    public void create(Booking booking) {
        if (vacantDuring(booking.period())) {
            this.bookings.add(booking);
        } else {
            throw new RuntimeException("The requested booking could not be completed, because the room is not vacant during that period.");
        }
    }

    public boolean vacantDuring(BookingPeriod period) {
        if (period == null) throw new RuntimeException("The provided booking period is invalid.");
        Optional<Booking> optionalOverlappingBooking = bookings
                                                        .parallelStream()
                                                        .filter(booking -> booking.overlaps(period))
                                                        .findAny();

        return optionalOverlappingBooking.isEmpty();
    }

    public Booking get(int index) {
        return this.bookings.get(index);
    }
}
