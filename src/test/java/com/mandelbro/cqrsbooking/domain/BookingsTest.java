package com.mandelbro.cqrsbooking.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingsTest {

    private Bookings bookings;

    @BeforeEach
    void setUp() {
        bookings = new Bookings();
    }

    @Test
    void size_shouldBeZeroOnCreation() {
        assertEquals(0, bookings.size());
    }

    @Test
    void size_shouldIncreaseWhenNewBookingIsCreated() {
        bookings.create(new Booking(new Client(), LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 17)));
        assertEquals(1, bookings.size());
    }

    @Test
    void create_shouldThrowExceptionIfProvidedBookingHasNoBookingPeriod() {
        Booking bookingWithNoPeriod = new Booking();
        assertThrows(RuntimeException.class, () -> {
            bookings.create(bookingWithNoPeriod);
        });
    }

    @Test
    void vacantDuring_shouldReturnTrueWhenNoBookingExists() {
        Booking booking = new Booking(new Client(), LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 17));
        assertTrue(bookings.vacantDuring(booking.period()));
    }

    @Test
    void vacantDuring_shouldReturnFalseWhenAlreadyBooked() {
        Booking booking = new Booking(new Client(), LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 17));
        bookings.create(booking);
        assertFalse(bookings.vacantDuring(booking.period()));
    }
}