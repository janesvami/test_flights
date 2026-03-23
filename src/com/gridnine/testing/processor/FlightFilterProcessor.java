package com.gridnine.testing.processor;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.util.FlightCheckUtil;

import java.util.List;
import java.util.function.Predicate;

public class FlightFilterProcessor {

    public List<Flight> applyAllFilters(List<Flight> flights, List<FlightFilter> filters) {
        if (flights == null){
            throw new IllegalArgumentException("Flights cannot be null");
        }

        if (filters == null || filters.isEmpty()) {
            throw new IllegalArgumentException("Filters cannot be null or empty");
        }
        Predicate<Flight> compositePredicate = flight -> true;
        for (FlightFilter filter : filters) {
            if (filter == null) {
                throw new IllegalArgumentException("Filter cannot be null");
            }
            compositePredicate = compositePredicate.and(filter);
        }
        return flights.stream()
                .peek(FlightCheckUtil::checkFlight)
                .filter(compositePredicate)
                .toList();
    }

    public List<Flight> applyFilter(List<Flight> flights, FlightFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filter cannot be null");
        }
        return applyAllFilters(flights, List.of(filter));
    }
}