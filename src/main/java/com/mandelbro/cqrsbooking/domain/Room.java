package com.mandelbro.cqrsbooking.domain;

public class Room {
    private Bookings bookings;

    public Room() {
        this.bookings = new Bookings();
    }

    public Bookings getBookings() {
        return this.bookings;
    }

    public void book(Booking booking) {
        this.bookings.create(booking);
    }

    public boolean isVacant(BookingPeriod bookingPeriod) {
        return this.bookings.vacantDuring(bookingPeriod);
    }
}
