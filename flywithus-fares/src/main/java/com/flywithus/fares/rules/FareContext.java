package com.flywithus.fares.rules;

import java.util.Set;

import com.flywithus.fares.ddd.Booking;
import com.flywithus.fares.ddd.Plane;
import com.flywithus.fares.ddd.Reservation;
import com.flywithus.fares.flight.Flight;
import com.flywithus.fares.flight.FlightInfo;
import com.flywithus.fares.flight.FlightInfo.PlaneInfo;

import lombok.Value;


@Value
public class FareContext 
{

  Flight flight;

  Set<Booking> bookings;

  Plane plane;

  Set<Reservation> reservations;

  public FlightInfo flightInfo()
  {
    return 
    	FlightInfo.builder().id(this.flight.getId()).number(this.flight.getNumber()).plane(
        PlaneInfo.builder().id(this.flight.getPlane().getId()).build()).build();
  }

}
