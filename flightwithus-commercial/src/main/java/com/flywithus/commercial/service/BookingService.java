package com.flywithus.commercial.service;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import com.flywithus.commercial.passenger.Passenger;
import com.flywithus.commercial.resource.data.BookingRequest;

import reactor.core.publisher.Flux;
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


  public Mono<ClientResponse> buyTicket(@NonNull BookingRequest bookingRequest)
  {
	  return this.webClient.mutate().baseUrl(this.bookingsService).build().post().body(BodyInserters.fromObject(bookingRequest)).exchange()
			  ;
  }

}
