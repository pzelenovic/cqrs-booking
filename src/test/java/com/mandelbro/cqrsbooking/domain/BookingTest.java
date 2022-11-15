package com.mandelbro.cqrsbooking.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    private Booking booking;
    private BookingPeriod period;
    private BookingPeriod overlappingPeriod;

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        assertThrows(RuntimeException.class, () -> {
            new Booking(new Client(), LocalDate.of(2032, 10, 10), LocalDate.of(2022, 10, 10));
        });
    }

    @Test
    void overlaps_shouldThrowExceptionWhenTheBookingPeriodIsNull() {
        booking = new Booking();
        assertThrows(RuntimeException.class, () -> {
            booking.overlaps(bookingPeriod(LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 11)));
        });
    }

    @Test
    void overlaps_shouldReturnFalseWhenThereIsNoOverlap() {
        booking = new Booking(new Client(), bookingPeriod(LocalDate.of(2021, 10, 10), LocalDate.of(2021, 10, 17)));
        period = bookingPeriod(LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 17));
        assertFalse(booking.overlaps(period));
    }

    @ParameterizedTest
    @CsvSource({
            "2022-10-10, 2022-10-17",
            "2022-10-17, 2022-10-31",
            "2021-10-17, 2032-10-31"
    })
    void overlaps_shouldReturnTrueWhenThereIsOverlapBefore(LocalDate startDate, LocalDate endDate) {
        booking = new Booking(new Client(), bookingPeriod(startDate.minusDays(2), endDate.minusDays(2)));
        overlappingPeriod = bookingPeriod(startDate, endDate);

        assertTrue(booking.overlaps(overlappingPeriod));
    }

    @ParameterizedTest
    @CsvSource({
            "2022-10-10, 2022-10-17",
            "2022-10-17, 2022-10-31",
            "2021-10-17, 2032-10-31"
    })
    void overlaps_shouldReturnTrueWhenThereIsOverlapAfter(LocalDate startDate, LocalDate endDate) {
        booking = new Booking(new Client(), bookingPeriod(startDate.plusDays(2), endDate.plusDays(2)));
        overlappingPeriod = bookingPeriod(startDate, endDate);

        assertTrue(booking.overlaps(overlappingPeriod));
    }

    @ParameterizedTest
    @CsvSource({
            "2022-10-10, 2022-10-17",
            "2022-10-17, 2022-10-31",
            "2021-10-17, 2032-10-31"
    })
    void overlaps_shouldReturnTrueWhenThereIsOverlapInMiddle(LocalDate startDate, LocalDate endDate) {
        booking = new Booking(new Client(), bookingPeriod(startDate.plusDays(2), endDate.minusDays(2)));
        overlappingPeriod = bookingPeriod(startDate, endDate);

        assertTrue(booking.overlaps(overlappingPeriod));
    }

    @ParameterizedTest
    @CsvSource({
            "2022-10-10, 2022-10-17",
            "2022-10-17, 2022-10-31",
            "2021-10-17, 2032-10-31"
    })
    void overlaps_shouldReturnTrueWhenThereIsOverlapOutside(LocalDate startDate, LocalDate endDate) {
        booking = new Booking(new Client(), startDate.minusDays(2), endDate.plusDays(2));
        overlappingPeriod = bookingPeriod(startDate, endDate);

        assertTrue(booking.overlaps(overlappingPeriod));
    }

    @ParameterizedTest
    @CsvSource({
            "2022-10-10, 2022-10-17",
            "2022-10-17, 2022-10-31",
            "2021-10-17, 2021-10-31"
    })
    void overlaps_shouldReturnFalseWhenComparingWithFuturePeriod(LocalDate startDate, LocalDate endDate) {
        booking = new Booking(new Client(), startDate.minusDays(180), endDate.minusDays(180));
        BookingPeriod futurePeriod = bookingPeriod(startDate, endDate);
        assertFalse(booking.overlaps(futurePeriod));
    }

    private BookingPeriod bookingPeriod(LocalDate startDate, LocalDate endDate) {
        return new BookingPeriod(startDate, endDate);
    }
}