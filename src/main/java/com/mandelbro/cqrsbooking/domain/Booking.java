package com.mandelbro.cqrsbooking.domain;

import java.time.LocalDate;

public class Booking {

    private BookingPeriod bookingPeriod;

    private Client client;

    public Booking() {
    }

    public Booking(Client client, BookingPeriod bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
        this.client = client;
    }

    public Booking(Client client, LocalDate start, LocalDate end) {
        if (start.isEqual(end) || start.isAfter(end)) {
            String helpfulMessage = "Booking period start must come before the booking period end, which is not the case.";
            throw new RuntimeException(helpfulMessage);
        }
        this.bookingPeriod = new BookingPeriod(start, end);
        this.client = client;
    }

    public BookingPeriod period() {
        return bookingPeriod;
    }

    ;

    public boolean overlaps(BookingPeriod otherPeriod) {
        if (this.bookingPeriod == null) throw new RuntimeException("There is no date set on this booking period.");
        return !(startsAndEndsAfter(otherPeriod) || startsAndEndsBefore(otherPeriod));
    }

    private boolean startsAndEndsAfter(BookingPeriod otherPeriod) {
        return this.bookingPeriod.getStart().isAfter(otherPeriod.getEnd())
                && this.bookingPeriod.getEnd().isAfter(otherPeriod.getEnd());
    }

    private boolean startsAndEndsBefore(BookingPeriod otherPeriod) {
        return this.bookingPeriod.getStart().isBefore(otherPeriod.getStart())
                && this.bookingPeriod.getEnd().isBefore(otherPeriod.getStart());
    }
}
