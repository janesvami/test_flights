package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GroundTimeExceedsFilter implements FlightFilter {
    @Override
    public List<Flight> apply(List<Flight> flights) {
        final List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight : flights) {
            long intervalsSum = 0;
            if (flight.getSegments().size() == 1) {
                filteredFlights.add(flight);
            } else {
                for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                    List<Segment> segments = flight.getSegments();
                    long interval = segments.get(i).getArrivalDate()
                            .until(segments.get(i + 1).getDepartureDate(), ChronoUnit.MINUTES);
                    intervalsSum += interval;
                }
                if (intervalsSum < 120) {
                    filteredFlights.add(flight);
                }
            }
        }
        return filteredFlights;
    }
}
