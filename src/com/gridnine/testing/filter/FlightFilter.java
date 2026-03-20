package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.function.Predicate;

public interface FlightFilter extends Predicate<Flight> {
}