package com.flywithus.fares.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.flywithus.fares.ddd.Booking;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class BookingService {

  private final String bookingService;

  private final WebClient webClient;
  
  private final ObjectMapper mapper;

  public BookingService( @Value("${flywithus.bookings.service}") String bookingService, WebClient webClient, ObjectMapper mapper) 
  {
    this.bookingService = bookingService;
    this.webClient = webClient;
    this.mapper = mapper;
  }


  public Mono<Set<Booking>> bookingOfFlight(String bookingId) {
    return this.webClient.mutate()
            .baseUrl(bookingService  + "/" + bookingId).build().get()
            .exchange()
            .flatMap(clientResponse ->
                {
                  final CollectionType type = mapper.getTypeFactory().
                      constructCollectionType(Set.class, Booking.class);
                  return clientResponse.bodyToMono(ParameterizedTypeReference.forType(type));
                }
            );
  }

}
