package com.flywithus.flights.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flywithus.flights.ddd.Airport;
import com.flywithus.flights.ddd.RegularPrice;

import lombok.Data;
import java.util.Set;


@Data
public class FlightRequest {

  Airport from;

  Airport to;

  @JsonProperty("departure_at")
  String departureAt;

  @JsonProperty("arrive_at")
  String arriveAt;

  @JsonProperty("plane_id")
  String planeId;

  Set<RegularPrice> prices;

}
