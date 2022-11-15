package com.mandelbro.cqrsbooking.domain;

import java.time.LocalDate;

public class BookingPeriod {

    private LocalDate start;
    private LocalDate end;

    public BookingPeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
