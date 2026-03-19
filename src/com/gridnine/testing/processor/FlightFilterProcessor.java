package com.gridnine.testing.processor;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.model.Flight;

import java.util.List;

public class FlightFilterProcessor {

    public List<Flight> runFilter(List<Flight> flights, FlightFilter filter) {
        if (flights == null || flights.isEmpty()) {
            return List.of();
        }
        return filter.apply(flights);
    }
}
