package com.mandelbro.cqrsbooking.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomsTest {

    private BookingPeriod bookingPeriod;
    private BookingPeriod twoDaysBefore;
    private BookingPeriod twoDaysAfter;
    private BookingPeriod muchEarlier;
    private BookingPeriod muchLater;
    private Rooms rooms;

    @Test
    void findAllFreeForPeriod_shouldReturnRoomsThatAreVacantInThePeriod() {
        LocalDate startDate = LocalDate.of(2022, 10, 10);
        LocalDate endDate = LocalDate.of(2022, 10, 20);
        setupBookingPeriods(startDate, endDate);

        Room vacantOne = new Room();
        Room vacantTwo = new Room();
        Room overlappingOne = new Room();
        Room overlappingTwo = new Room();
        Room overlappingThree = new Room();

        Booking vacantOneBooking = new Booking(new Client(), muchEarlier);
        Booking vacantTwoBooking = new Booking(new Client(), muchLater);
        Booking overlappingOneBooking = new Booking(new Client(), twoDaysBefore);
        Booking overlappingTwoBooking = new Booking(new Client(), bookingPeriod);
        Booking overlappingThreeBooking = new Booking(new Client(), twoDaysAfter);

        vacantOne.book(vacantOneBooking);
        vacantTwo.book(vacantTwoBooking);
        overlappingOne.book(overlappingOneBooking);
        overlappingTwo.book(overlappingTwoBooking);
        overlappingThree.book(overlappingThreeBooking);

        rooms = new Rooms();
        rooms.addAll(vacantOne, vacantTwo, overlappingOne, overlappingTwo, overlappingThree);

        Rooms expectedRooms = new Rooms();
        expectedRooms.add(vacantOne);
        expectedRooms.add(vacantTwo);

        assertEquals(expectedRooms.totalNumber(), rooms.findVacantDuringPeriod(bookingPeriod).totalNumber());
    }


    @Test
    void addAll_shouldAddAllRoomsToRoomsCollection() {
        LocalDate startDate = LocalDate.of(2022, 10, 10);
        LocalDate endDate = LocalDate.of(2022, 10, 20);
        setupBookingPeriods(startDate, endDate);

        Room vacantOne = new Room();
        Room vacantTwo = new Room();
        Room overlappingOne = new Room();
        Room overlappingTwo = new Room();
        Room overlappingThree = new Room();

        Booking vacantOneBooking = new Booking(new Client(), muchEarlier);
        Booking vacantTwoBooking = new Booking(new Client(), muchLater);
        Booking overlappingOneBooking = new Booking(new Client(), twoDaysBefore);
        Booking overlappingTwoBooking = new Booking(new Client(), bookingPeriod);
        Booking overlappingThreeBooking = new Booking(new Client(), twoDaysAfter);

        vacantOne.book(vacantOneBooking);
        vacantTwo.book(vacantTwoBooking);
        overlappingOne.book(overlappingOneBooking);
        overlappingTwo.book(overlappingTwoBooking);
        overlappingThree.book(overlappingThreeBooking);

        Rooms rooms = new Rooms();
        rooms.addAll(vacantOne, vacantTwo, overlappingOne, overlappingTwo, overlappingThree);

        assertEquals(5, rooms.totalNumber());
    }

    private void setupBookingPeriods(LocalDate startDate, LocalDate endDate) {
        bookingPeriod = new BookingPeriod(startDate, endDate);
        twoDaysBefore = new BookingPeriod(startDate.minusDays(2),
                                                        endDate.minusDays(2));
        twoDaysAfter = new BookingPeriod(startDate.plusDays(2),
                                                        endDate.plusDays(2));
        muchEarlier = new BookingPeriod(startDate.minusDays(90),
                                                        endDate.minusDays(90));
        muchLater = new BookingPeriod(startDate.plusDays(90),
                                                    endDate.plusDays(90));
    }
}
