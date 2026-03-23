package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroundTimeExceedsFilterTest {
    private final GroundTimeExceedsFilter filter = new GroundTimeExceedsFilter();

    @Test
    void test_WhenTwoSegmentsWithTransferMoreThanTwoHours_ThenReturnsFalse() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 15, 0),
                LocalDateTime.of(2026, 1, 1, 17, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2));

        assertFalse(filter.test(flight));
    }

    @Test
    void test_WhenThreeSegmentsWithTransferMoreThanTwoHours_ThenReturnsFalse() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 13, 0),
                LocalDateTime.of(2026, 1, 1, 15, 0)
        );
        Segment segment3 = new Segment(
                LocalDateTime.of(2026, 1, 1, 17, 0),
                LocalDateTime.of(2026, 1, 1, 19, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2, segment3));

        assertFalse(filter.test(flight));
    }

    @Test
    void test_WhenSegmentsOverlap_ThenNotCountOverlappingTime() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 13, 0),
                LocalDateTime.of(2026, 1, 1, 18, 0)
        );
        Segment invalidSegment = new Segment(
                LocalDateTime.of(2026, 1, 1, 16, 0),
                LocalDateTime.of(2026, 1, 1, 19, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2, invalidSegment));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenSingleSegment_ThenReturnsTrue() {
        Segment segment = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Flight flight = new Flight(List.of(segment));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenTwoSegmentsWithTransferLessThanTwoHours_ThenReturnsTrue() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 13, 0),
                LocalDateTime.of(2026, 1, 1, 15, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenTwoSegmentsWithTransferTwoHours_ThenReturnsTrue() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 14, 0),
                LocalDateTime.of(2026, 1, 1, 15, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenThreeSegmentsWithTransferLessThanTwoHours_ThenReturnsTrue() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 12, 30),
                LocalDateTime.of(2026, 1, 1, 15, 0)
        );
        Segment segment3 = new Segment(
                LocalDateTime.of(2026, 1, 1, 15, 30),
                LocalDateTime.of(2026, 1, 1, 17, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2, segment3));

        assertTrue(filter.test(flight));
    }

    @Test
    void test_WhenThreeSegmentsWithTransferTwoHours_ThenReturnsTrue() {
        Segment segment1 = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Segment segment2 = new Segment(
                LocalDateTime.of(2026, 1, 1, 13, 0),
                LocalDateTime.of(2026, 1, 1, 15, 0)
        );
        Segment segment3 = new Segment(
                LocalDateTime.of(2026, 1, 1, 16, 0),
                LocalDateTime.of(2026, 1, 1, 17, 0)
        );
        Flight flight = new Flight(List.of(segment1, segment2, segment3));

        assertTrue(filter.test(flight));
    }
}