package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrivalBeforeDepartureFilterTest {
    private final ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();

    @Test
    void test_WhenSingleSegmentThatArrivesBeforeDeparture_ThenReturnsFalse() {
        Segment invalidSegment = new Segment(
                LocalDateTime.of(2026, 1, 1, 12, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0)
        );
        Flight flight = new Flight(List.of(invalidSegment));

        assertFalse(filter.test(flight));
    }

    @Test
    void test_WhenMultipleSegmentsThatArriveBeforeDeparture_ThenReturnsFalse() {
        Segment invalidSegment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 12, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0)
        );
        Segment invalidSegment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 14, 0),
                LocalDateTime.of(2026, 1, 1, 16, 0)
        );
        Flight flight = new Flight(List.of(invalidSegment1, invalidSegment2));

        assertFalse(filter.test(flight));
    }

    @Test
    void test_WhenMultipleSegmentsOneOfWhichArrivesBeforeDeparture_ThenReturnsFalse() {
        Segment invalidSegment = new Segment(
                LocalDateTime.of(2026, 1, 1, 12, 0),
                LocalDateTime.of(2026, 1, 1, 10, 0)
        );
        Segment validSegment = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Flight flight = new Flight(List.of(invalidSegment, validSegment));

        assertFalse(filter.test(flight));
    }

    @Test
    void test_WhenSingleSegmentThatArrivesAfterDeparture_ThenReturnsTrue() {
        Segment validSegment = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Flight flight = new Flight(List.of(validSegment));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenMultipleSegmentsThatArriveAfterDeparture_ThenReturnsTrue() {
        Segment validSegment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment validSegment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 14, 0),
                LocalDateTime.of(2026, 1, 1, 16, 0)
        );
        Flight flight = new Flight(List.of(validSegment1, validSegment2));

        assertTrue(filter.test(flight));
    }
}