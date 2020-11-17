package com.flywithus.fares.ddd;

import java.util.Set;

import com.flywithus.fares.flight.Flight;

import lombok.Data;



@Data
public class Booking {

  String id;

  Set<Seat> passengers;

  Flight flight;

  public Long reservationsOfClass(String classId){
    return this.passengers.stream().filter(seat -> seat.getCategory().getId().equalsIgnoreCase(classId)).count();
  }

}