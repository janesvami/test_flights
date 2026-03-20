package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

public class ArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public boolean test(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                return false;
            }
        }
        return true;
    }
}