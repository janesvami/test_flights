package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class DepartureBeforeNowFilter implements FlightFilter {
    @Override
    public List<Flight> apply(List<Flight> flights) {
        return flights.stream()
                .filter(f -> f.getSegments()
                        .stream()
                        .noneMatch(s -> s.getDepartureDate().isBefore(LocalDateTime.now()))
                )
                .toList();
    }
}