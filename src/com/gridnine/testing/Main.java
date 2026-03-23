package com.gridnine.testing;

import com.gridnine.testing.data.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.GroundTimeExceedsFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.processor.FlightFilterProcessor;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        FlightFilterProcessor processor = new FlightFilterProcessor();
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter departedBeforeNowFilter = new DepartureBeforeNowFilter();
        FlightFilter arrivedBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
        FlightFilter groundTimeExceededFilter = new GroundTimeExceedsFilter();

        List<FlightFilter> allFilters = List.of(
                departedBeforeNowFilter,
                arrivedBeforeDepartureFilter,
                groundTimeExceededFilter
        );
        List<Flight> departedBeforeNowFlights = processor.applyFilter(flights, departedBeforeNowFilter);
        printFlights(departedBeforeNowFlights, departedBeforeNowFilter);
        List<Flight> arrivedBeforeDepartureFlights = processor.applyFilter(flights, arrivedBeforeDepartureFilter);
        printFlights(arrivedBeforeDepartureFlights, arrivedBeforeDepartureFilter);
        List<Flight> groundTimeNotExceededFlights = processor.applyFilter(flights, groundTimeExceededFilter);
        printFlights(groundTimeNotExceededFlights, groundTimeExceededFilter);

        List<Flight> flightsByAllFilters = processor.applyAllFilters(flights, allFilters);
        printFlights(flightsByAllFilters, allFilters);
    }

    private static void printFlights(List<Flight> flights, List<FlightFilter> filters) {
        String filterNames = filters.stream()
                .map(filter -> filter.getClass().getSimpleName())
                .collect(Collectors.joining(", "));
        System.out.println("Filtered flights by filters: " + filterNames);
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        System.out.println("=============================");
    }

    private static void printFlights(List<Flight> flights, FlightFilter filter){
        printFlights(flights, List.of(filter));
    }
}
