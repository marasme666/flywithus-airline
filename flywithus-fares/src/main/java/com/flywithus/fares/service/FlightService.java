package com.flywithus.fares.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.fares.flight.Flight;

import reactor.core.publisher.Mono;


@Service
public class FlightService {

  private final String flightService;

  private final WebClient webClient;

  public FlightService( @Value("${flywithus.flights.service}") String flightService, WebClient webClient) 
  {
    this.flightService = flightService;
    this.webClient = webClient;
  }


  public Mono<Flight> flight(String id) {
    return this.webClient.mutate().baseUrl(this.flightService + "/" + id).build().get().retrieve()
            .bodyToMono(Flight.class);
  }

}
