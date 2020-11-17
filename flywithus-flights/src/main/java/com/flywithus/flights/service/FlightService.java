package com.flywithus.flights.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import com.flywithus.flights.ddd.Flight;
import com.flywithus.flights.ddd.Plane;
import com.flywithus.flights.repository.FlightRepository;
import com.flywithus.flights.resource.data.FlightQuery;
import com.flywithus.flights.resource.data.FlightRequest;
import com.flywithus.flights.service.data.AvailableSeats;
import com.flywithus.flights.service.data.TotalBooked;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class FlightService {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private final FlightRepository flightRepository;

  private final PlaneService planeService;

  private final BookingService bookingService;

  public FlightService(FlightRepository flightRepository, PlaneService planeService, BookingService bookingService) 
  {
    this.flightRepository = flightRepository;
    this.planeService = planeService;
    this.bookingService = bookingService;
  }

  public Mono<Flight> flight(String id) 
  {
    return this.flightRepository.findById(id);
  }

  public Flux<Flight> flights() 
  {
    return this.flightRepository.findAll();
  }

  public Mono<Flight> save(@NonNull FlightRequest flight) 
  {
    return this.planeService.plane(flight.getPlaneId())
        .flatMap(plane -> this.flightRepository.save(Flight.builder()
            .from(flight.getFrom())
            .to(flight.getTo())
            .plane(plane)
            .arriveAt(flight.getArriveAt())
            .departureAt(flight.getDepartureAt())
            .prices(flight.getPrices())
            .build()));
  }

  public Mono<Void> deleteFlight(@NonNull Flight flight) 
  {
    return this.flightRepository.delete(flight);
  }

  public Mono<Flight> update(String id, FlightRequest flightRequest) 
  {
    return this.flightRepository.findById(id)
        .flatMap(data -> this.flightRepository.save(data.fromFlightRequest(flightRequest)));
  }

  public Flux<Flight> flightBy(@NonNull FlightQuery query) 
  {
    final LocalDateTime departureAt = LocalDateTime.parse(query.getDepartureAt(), FORMATTER);
    final LocalDateTime arriveAt = LocalDateTime.parse(query.getArriveAt(), FORMATTER);
    return this.flightRepository.findByFromCodeAndToCodeAndDepartureAtAfterAndAndArriveAtBefore(query.getFrom(),query.getTo(),departureAt,arriveAt);
  }

  public Mono<AvailableSeats> availableSeats(String flightId)
  {
    return this.flightRepository.findById(flightId)
        .flatMap(flight -> Mono.zip(this.planeService.plane(flight.getPlane().getId()),this.bookingService.totalBooked(flightId)))
        .map(data ->{
          final Plane plane = data.getT1();
          final TotalBooked totalBooked = data.getT2();
          return AvailableSeats.builder().available(plane.numberOfAvailableSeats() - totalBooked.getTotal()).flightId(flightId).build();
        });
  }

}
