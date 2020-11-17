package com.flywithus.planes.resource.data;

import java.util.Set;

import com.flywithus.planes.ddd.PlaneModel;
import com.flywithus.planes.ddd.Seat;

import lombok.Data;


@Data
public class PlaneRequest {

  String owner;

  PlaneModel model;

  Set<Seat> seats;

  String notes;

}