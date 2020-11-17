package com.flywithus.flights.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.flights.service.data.TotalBooked;

import reactor.core.publisher.Mono;


@Service
public class BookingService {

  private final WebClient webClient;

  private final String bookingsService;

  public BookingService(WebClient webClient, @Value("${flywithus.bookings.service}") String bookingsService) 
  {
    this.webClient = webClient;
    this.bookingsService = bookingsService;
  }


  public Mono<TotalBooked> totalBooked(String flightId) 
  {	  
	    return this.webClient.mutate().baseUrl(this.bookingsService + "/flights/" + flightId).build().get().retrieve()
	            .bodyToMono(TotalBooked.class);	  
	 
  }

}
