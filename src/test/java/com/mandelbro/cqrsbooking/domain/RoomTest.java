package com.mandelbro.cqrsbooking.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private Room room;
    private Client client = new Client();
    private BookingPeriod bookingPeriod;
    private Booking booking;

    @Test
    void getBookings_shouldReturnNoBookingsAtRoomCreation() {
        room = new Room();
        assertEquals(0, room.getBookings().size());
    }

    @Test
    void getBookings_shouldReturnExistingBookings() {
        room = new Room();
        Booking booking1 = new Booking(client, LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 20));
        Booking booking2 = new Booking(client, LocalDate.of(2022, 10, 21), LocalDate.of(2022, 10, 31));
        Booking booking3 = new Booking(client, LocalDate.of(2022, 11, 1), LocalDate.of(2022, 11, 10));
        room.book(booking1);
        room.book(booking2);
        room.book(booking3);

        Bookings foundBookings = room.getBookings();

        assertEquals(3, foundBookings.size());
        assertEquals(booking1, foundBookings.get(0));
        assertEquals(booking2, foundBookings.get(1));
        assertEquals(booking3, foundBookings.get(2));
    }

    @Test
    void isVacant_shouldReturnTrueWhenNoBookingOverlapsGivenPeriod() {
        room = new Room();
        bookingPeriod = createBookingPeriod();

        assertTrue(room.isVacant(bookingPeriod));
    }


    @Test
    void isVacant_shouldReturnFalseWhenAlreadyBookedForPeriod() {
        room = new Room();
        bookingPeriod = createBookingPeriod();
        booking = new Booking(client, bookingPeriod);
        room.book(booking);

        assertThrows(RuntimeException.class, () -> {
            room.book(booking);
        });
    }

    private static BookingPeriod createBookingPeriod() {
        return new BookingPeriod(LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 20));
    }
}
