package com.flywithus.fares.ddd;

import java.util.Set;

import com.flywithus.fares.flight.FlightRequest;

import lombok.Data;


@Data
public class FareQuery {

  Passenger mainPassenger;

  Set<Reservation> reservations;

  FlightRequest flight;

}
