package com.gridnine.testing.util;

import com.gridnine.testing.exception.InvalidDataException;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FlightCheckUtilTest {

    @Test
    void checkFlight_whenFlightIsNull_ThenThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class,
                () -> FlightCheckUtil.checkFlight(null));
    }

    @Test
    void checkFlight_whenSegmentListIsNull_ThenThrowsInvalidDataException() {
        Flight flight = new Flight(null);

        assertThrows(InvalidDataException.class,
                () -> FlightCheckUtil.checkFlight(flight));
    }

    @Test
    void checkFlight_whenSegmentListIsEmpty_ThenThrowsInvalidDataException() {
        Flight flight = new Flight(List.of());

        assertThrows(InvalidDataException.class,
                () -> FlightCheckUtil.checkFlight(flight));
    }

    @Test
    void checkFlight_whenSegmentIsNull_ThenThrowsInvalidDataException() {
        List<Segment> segments = new ArrayList<>();
        segments.add(null);
        Flight flight = new Flight(segments);

        assertThrows(InvalidDataException.class,
                () -> FlightCheckUtil.checkFlight(flight));
    }

    @Test
    void checkFlight_whenAllDataIsValid_ThenNoException() {
        Segment segment = new Segment(
                LocalDateTime.of(2026, 1, 1, 10, 0),
                LocalDateTime.of(2026, 1, 1, 12, 0)
        );
        Flight flight = new Flight(List.of(segment));

        assertDoesNotThrow(() -> FlightCheckUtil.checkFlight(flight));
    }
}