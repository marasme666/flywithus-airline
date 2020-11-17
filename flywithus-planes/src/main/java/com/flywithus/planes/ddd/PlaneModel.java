package com.flywithus.planes.ddd;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaneModel {
 
  String factory;
 
  String model;
 
  String name;
 
  @JsonProperty("reference_name")
  String referenceName;
 
}

