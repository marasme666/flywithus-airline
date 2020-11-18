package com.flywithus.bookings.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flywithus.bookings.ddd.Booking;
import com.flywithus.bookings.service.BookingService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
public class BoardingPassResource {

  private final BookingService bookingService;

  public BoardingPassResource(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/{id}/boarding-pass")
  public Mono<ResponseEntity<Booking>> boardingPass(@PathVariable("id")String id){
    return this.bookingService.byId(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
