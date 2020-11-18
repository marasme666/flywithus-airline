package com.flywithus.commercial.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.flywithus.commercial.resource.data.PassengerRequest;
import com.flywithus.commercial.service.PassengerService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
public class PassengerResource {

  private final PassengerService passengerService;

  public PassengerResource(PassengerService passengerService) {
    this.passengerService = passengerService;
  }

  @PostMapping("/register")
  public Mono<ResponseEntity<Void>> register(@Valid @RequestBody PassengerRequest passengerRequest, UriComponentsBuilder uriBuilder){
    return this.passengerService.newPassenger(passengerRequest).map(data -> {
      URI location = uriBuilder.path("/passengers/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

}
