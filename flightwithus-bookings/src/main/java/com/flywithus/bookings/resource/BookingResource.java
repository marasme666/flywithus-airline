package com.flywithus.bookings.resource;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.flywithus.bookings.ddd.Booking;
import com.flywithus.bookings.resource.data.BookingRequest;
import com.flywithus.bookings.service.BookingService;
import com.flywithus.bookings.service.data.TotalBooked;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
public class BookingResource {

  private final BookingService bookingService;

  public BookingResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/flight/{flightId}")
  public Flux<Booking> bookingsOfFlight(@PathVariable("flightId")String flightId){
    return this.bookingService.bookingsOfFlight(flightId);
  }

  @GetMapping("/{flightId}/total")
  public Mono<TotalBooked> totalBooked(@PathVariable("flightId")String flightId){
    return this.bookingService.totalBooked(flightId);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Object>> cancel(@PathVariable("id")String id){
    return this.bookingService.cancelBooking(id).map(el -> ResponseEntity.noContent().build()).defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Void>> newBooking(@Valid @RequestBody BookingRequest bookingRequest, UriComponentsBuilder uriBuilder){
    return this.bookingService.newBooking(bookingRequest.getFareId()).map(data -> {
      URI location = uriBuilder.path("/bookings/{id}")
          .buildAndExpand(data.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    });
  }

}
