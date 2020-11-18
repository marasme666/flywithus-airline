package com.flywithus.bookings.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flywithus.bookings.ddd.Booking;

import reactor.core.publisher.Flux;


public interface BookingRepository extends ReactiveCrudRepository<Booking,String> {

  Flux<Booking> findByFlightId(String flightId);

}
