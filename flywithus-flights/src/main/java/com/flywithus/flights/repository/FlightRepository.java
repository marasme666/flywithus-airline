package com.flywithus.flights.repository;

import java.time.LocalDateTime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flywithus.flights.ddd.Flight;

import reactor.core.publisher.Flux;

public interface FlightRepository extends ReactiveCrudRepository<Flight,String>{

  Flux<Flight> findByFromCodeAndToCodeAndDepartureAtAfterAndAndArriveAtBefore(String fromCode,String toCode,LocalDateTime departure,LocalDateTime arriveAt);

}