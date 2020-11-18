package com.flywithus.commercial.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.util.UriComponentsBuilder;

import com.flywithus.commercial.resource.data.BookingRequest;
import com.flywithus.commercial.service.BookingService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class BuyTicketResource {

  private final BookingService bookingService;

  public BuyTicketResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping("/tickets")
  public Mono<ClientResponse> register(@Valid @RequestBody BookingRequest bookingRequest)
  {
    return this.bookingService.buyTicket(bookingRequest);
  }

}
