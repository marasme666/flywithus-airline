package com.flywithus.bookings.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.bookings.ddd.Plane;

import reactor.core.publisher.Mono;


@Service
public class PlaneService {
 
  private final WebClient webClient;
 
  private final String planesService;
 
  public PlaneService(WebClient webClient, @Value("${flywithus.planes.service}") String planesService) 
  {
    this.webClient = webClient;
    this.planesService = planesService;
  }
 
  public Mono<Plane> plane(String id) 
  {
         return this.webClient.mutate().baseUrl(this.planesService + "/" + id).build().get().retrieve().bodyToMono(Plane.class);
  }
 
}
