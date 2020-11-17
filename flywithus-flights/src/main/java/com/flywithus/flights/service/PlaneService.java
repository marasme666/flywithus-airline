package com.flywithus.flights.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.flights.ddd.Plane;
import com.flywithus.flights.exception.PlaneNotFoundException;

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
    return this.webClient.mutate().baseUrl(this.planesService + "/" + id).build().get().retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new PlaneNotFoundException(id)))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new PlaneNotFoundException(id)))
            .bodyToMono(Plane.class);
  }

}
