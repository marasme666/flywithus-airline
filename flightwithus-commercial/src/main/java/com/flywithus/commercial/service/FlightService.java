package com.flywithus.commercial.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FlightService {

  private final WebClient webClient;

  private final String flightsService;

  public FlightService(WebClient webClient, @Value("${flywithus.flights.service}") String flightsService) 
  {
    this.webClient = webClient;
    this.flightsService = flightsService;
  }


	/*
	 * public Flux<Flight> search(@NonNull FlightSearch query) {
	 * 
	 * }
	 * 
	 * 
	 * public Mono<AvailableSeats> availableSeats(@NonNull String flightId) {
	 * 
	 * }
	 */

}
