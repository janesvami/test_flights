package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GroundTimeExceedsFilter implements FlightFilter {
    private static final long TWO_HOURS_IN_NANOS = 7_200_000_000_000L;

    @Override
    public boolean test(Flight flight) {
        final List<Segment> segments = flight.getSegments();
        final int numberOfSegments = segments.size();
        long intervalsSum = 0;
        if (numberOfSegments != 1) {
            for (int i = 0; i < numberOfSegments - 1; i++) {
                final Segment currentSegment = segments.get(i);
                final Segment nextSegment = segments.get(i + 1);
                final LocalDateTime currentSegmentArrivalDate = currentSegment.getArrivalDate();
                final LocalDateTime nextSegmentDepartureDate = nextSegment.getDepartureDate();

                if (nextSegmentDepartureDate.isBefore(currentSegmentArrivalDate)) {
                    continue;
                }
                final long interval = ChronoUnit.NANOS.between(
                        currentSegmentArrivalDate,
                        nextSegmentDepartureDate
                );
                intervalsSum += interval;

                if (intervalsSum > TWO_HOURS_IN_NANOS) {
                    return false;
                }
            }
        }
        return true;
    }
}