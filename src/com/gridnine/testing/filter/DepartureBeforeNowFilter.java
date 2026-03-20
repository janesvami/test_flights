package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class DepartureBeforeNowFilter implements FlightFilter {
    public boolean test(Flight flight) {
        final List<Segment> segments = flight.getSegments();
        final Segment firstSegment = segments.getFirst();
        return !firstSegment.getDepartureDate().isBefore(LocalDateTime.now());
    }
}