package com.flywithus.fares.ddd;

import java.time.LocalDateTime;
import java.util.Set;

import com.flywithus.fares.flight.FlightInfo;

import lombok.Builder;
import lombok.Data;



@Data
public class Fare {

  String id;

  LocalDateTime validUntil;

  LocalDateTime createdAt;

  Set<Reservation> reservations;

  FlightInfo flight;

  @Builder
  public static Fare newFare(String id,LocalDateTime validUntil,Set<Reservation> reservations, FlightInfo flight){
    Fare fare = new Fare();
    fare.id = id;
    fare.validUntil = validUntil;
    fare.createdAt = LocalDateTime.now();
    fare.reservations = reservations;
    fare.flight = flight;
    return fare;
  }

}
