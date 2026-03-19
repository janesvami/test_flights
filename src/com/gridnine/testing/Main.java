package com.gridnine.testing;

import com.gridnine.testing.data.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.filter.GroundTimeExceedsFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.processor.FlightFilterProcessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightFilterProcessor processor = new FlightFilterProcessor();
        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter departedBeforeNowFilter = new DepartureBeforeNowFilter();
        List<Flight> notDepartedFlights = processor.runFilter(flights, departedBeforeNowFilter);
        printFlights(notDepartedFlights);

        FlightFilter arrivedBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
        List<Flight> validFlights = processor.runFilter(flights, arrivedBeforeDepartureFilter);
        printFlights(validFlights);

        FlightFilter groundTimeExceededFilter = new GroundTimeExceedsFilter();
        List<Flight> groundTimeNotExceededFlights = processor.runFilter(flights, groundTimeExceededFilter);
        printFlights(groundTimeNotExceededFlights);
    }

    private static void printFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        System.out.println("=============================");
    }
}
