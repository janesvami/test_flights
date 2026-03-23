package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartureBeforeNowFilterTest {
    private final DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();

    @Test
    void test_WhenFlightDepartureIsBeforeNow_ThenReturnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        Segment segmentPast = new Segment(now.minusHours(3), now.minusHours(1));
        Flight flightPast = new Flight(List.of(segmentPast));

        assertFalse(filter.test(flightPast));
    }

    @Test
    void test_WhenFlightDepartureIsAfterNow_ThenReturnsTrue() {
        LocalDateTime now = LocalDateTime.now();
        Segment segmentFuture = new Segment(now.plusHours(1), now.plusHours(3));
        Flight flightFuture = new Flight(List.of(segmentFuture));

        assertTrue(filter.test(flightFuture));
    }
}