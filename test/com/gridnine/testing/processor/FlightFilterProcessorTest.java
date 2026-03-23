package com.gridnine.testing.processor;

import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightFilterProcessorTest {
    private final FlightFilterProcessor processor = new FlightFilterProcessor();
    private final FlightFilter alwaysTrue = flight -> true;
    private final FlightFilter alwaysFalse = flight -> false;

    @Test
    void applyAllFilters_WhenFlightsListIsNull_ThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.applyAllFilters(null, List.of()));
    }

    @Test
    void applyAllFilters_WhenFiltersListIsNull_ThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.applyAllFilters(List.of(), null));
    }

    @Test
    void applyAllFilters_WhenFiltersListIsEmpty_ThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.applyAllFilters(List.of(), List.of()));
    }

    @Test
    void applyAllFilters_WhenFilterIsNull_ThenThrowsIllegalArgumentException() {
        List<FlightFilter> filters = new ArrayList<>();
        filters.add(null);

        assertThrows(IllegalArgumentException.class,
                () -> processor.applyAllFilters(List.of(), filters));
    }

    @Test
    void applyAllFilters_WhenFiltersExcludedAllFlights_ThenReturnsEmptyList() {
        List<FlightFilter> filters = List.of(alwaysFalse, alwaysFalse);
        LocalDateTime now = LocalDateTime.now();
        Segment segmentPast = new Segment(now.minusHours(3), now.minusHours(1));
        Segment segmentFuture = new Segment(now.plusHours(1), now.plusHours(3));
        Flight flightPast = new Flight(List.of(segmentPast));
        Flight flightFuture = new Flight(List.of(segmentFuture));
        List<Flight> flights = List.of(flightPast, flightFuture);

        List<Flight> result = processor.applyAllFilters(flights, filters);

        assertTrue(result.isEmpty());
    }

    @Test
    void applyAllFilters_WhenAllFlightsPassedFilters_ThenReturnsOriginalList() {
        List<FlightFilter> filters = List.of(alwaysTrue, alwaysTrue);
        LocalDateTime now = LocalDateTime.now();
        Segment segmentPast = new Segment(now.minusHours(3), now.minusHours(1));
        Segment segmentFuture = new Segment(now.plusHours(1), now.plusHours(3));
        Flight flightPast = new Flight(List.of(segmentPast));
        Flight flightFuture = new Flight(List.of(segmentFuture));
        List<Flight> flights = List.of(flightPast, flightFuture);

        List<Flight> result = processor.applyAllFilters(flights, filters);

        assertEquals(flights, result);
    }

    @Test
    void applyAllFilters_WhenSingleFilter_ThenReturnsListThatPassed() {
        FlightFilter filter = new DepartureBeforeNowFilter();
        LocalDateTime now = LocalDateTime.now();
        Segment segmentPast = new Segment(now.minusHours(3), now.minusHours(1));
        Segment segmentFuture = new Segment(now.plusHours(1), now.plusHours(3));
        Flight flightPast = new Flight(List.of(segmentPast, segmentPast));
        Flight flightFuture = new Flight(List.of(segmentFuture, segmentFuture));
        List<Flight> flights = List.of(flightPast, flightFuture);
        List<FlightFilter> filters = List.of(filter);

        List<Flight> result = processor.applyAllFilters(flights, filters);

        assertTrue(result.contains(flightFuture));
        assertFalse(result.contains(flightPast));
    }

    @Test
    void applyAllFilters_WhenMultipleFilters_ThenReturnsListThatPassedAllFilters() {
        FlightFilter filter = new DepartureBeforeNowFilter();
        FlightFilter filter2 = new ArrivalBeforeDepartureFilter();
        LocalDateTime now = LocalDateTime.now();
        Segment segmentPast = new Segment(now.minusHours(3), now.minusHours(1));
        Segment segmentFuture = new Segment(now.plusHours(1), now.plusHours(3));
        Segment invalidSegmentFuture = new Segment(now.plusHours(3), now);
        Flight flightPast = new Flight(List.of(segmentPast));
        Flight flightFuture = new Flight(List.of(segmentFuture));
        Flight invalidFlightFuture = new Flight(List.of(invalidSegmentFuture));
        List<Flight> flights = List.of(flightPast, flightFuture, invalidFlightFuture);
        List<FlightFilter> filters = List.of(filter, filter2);

        List<Flight> result = processor.applyAllFilters(flights, filters);

        assertTrue(result.contains(flightFuture));
        assertFalse(result.contains(invalidFlightFuture));
        assertFalse(result.contains(flightPast));
    }

    @Test
    void applyFilter_WhenFilterIsValid_ThenReturnsList() {
        List<Flight> result = processor.applyFilter(List.of(), alwaysTrue);

        assertNotNull(result);
    }

    @Test
    void applyFilter_WhenFilterIsNull_ThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.applyFilter(List.of(), null));
    }
}