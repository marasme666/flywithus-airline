package com.flywithus.bookings.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.bookings.fare.Fare;

import reactor.core.publisher.Mono;


@Service
public class FareService {
 
  private final String fareService;
 
  private final WebClient webClient;
 
  public FareService( WebClient webClient, @Value("${flywithus.fares.service}") String fareService) 
  {
    this.fareService = fareService;
    this.webClient = webClient;
  }
 
  public Mono<Fare> fare(String id)
  {
         return this.webClient.mutate().baseUrl(this.fareService + "/" + id).build().get().retrieve().bodyToMono(Fare.class); 
  }
 
}
