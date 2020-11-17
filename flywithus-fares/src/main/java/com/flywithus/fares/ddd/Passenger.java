package com.flywithus.fares.ddd;

import lombok.Data;



@Data
public class Passenger {

  String id;

  String name;

  String email;

  PassengerDocument document;

}
