package com.gridnine.testing.util;

import com.gridnine.testing.exception.InvalidDataException;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.util.List;

public final class FlightCheckUtil {
    private FlightCheckUtil(){}

    public static void checkFlight(Flight flight) {
        if (flight == null) {
            throw new InvalidDataException("Flight cannot be null");
        }
        List<Segment> segments = flight.getSegments();
        if (segments == null || segments.isEmpty()) {
            throw new InvalidDataException("Flight segments cannot be null or empty");
        }

        segments.forEach(segment -> {
            if (segment == null) {
                throw new InvalidDataException("Segment cannot be null");
            }
            if (segment.getArrivalDate() == null) {
                throw new InvalidDataException("Arrival date cannot be null");
            }
            if (segment.getDepartureDate() == null) {
                throw new InvalidDataException("Departure date cannot be null");
            }
        });
    }
}
