package com.flywithus.flights.ddd;

import lombok.Data;

@Data
public class Seat {

  String identity;

  Integer row;

  Class category;

}
