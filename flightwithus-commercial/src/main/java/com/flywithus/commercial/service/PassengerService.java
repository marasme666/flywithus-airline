package com.flywithus.commercial.service;


import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.flywithus.commercial.passenger.Passenger;
import com.flywithus.commercial.resource.data.PassengerRequest;

import reactor.core.publisher.Mono;

@Service
public class PassengerService {

  private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  private final WebClient webClient;

  private final String passengersService;

  public PassengerService(WebClient webClient,
      @Value("${flywithus.passangers.service}")String passengersService) {
    this.webClient = webClient;
    this.passengersService = passengersService;
  }


  public Mono<Passenger> newPassenger(@NonNull PassengerRequest passengerRequest)
  {
	  return this.webClient.mutate().baseUrl(this.passengersService).build().post().body(BodyInserters.fromObject(passengerRequest))
			  .exchange()
			  .flatMap(res -> res.bodyToMono(Passenger.class));
  }

}
