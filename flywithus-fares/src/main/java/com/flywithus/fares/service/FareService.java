package com.flywithus.fares.service;

import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.val;
import org.springframework.stereotype.Service;

import com.flywithus.fares.ddd.Booking;
import com.flywithus.fares.ddd.Fare;
import com.flywithus.fares.ddd.FareQuery;
import com.flywithus.fares.ddd.Reservation;
import com.flywithus.fares.flight.Flight;
import com.flywithus.fares.repository.FareRepository;
import com.flywithus.fares.rules.FareContext;

import reactor.core.publisher.Mono;


@Service
public class FareService {

  private final BookingService bookingService;

  private final FlightService flightService;

  private final FareRepository fareRepository;

  public FareService(BookingService bookingService, FlightService flightService, FareRepository fareRepository) 
  {
    this.bookingService = bookingService;
    this.flightService = flightService;
    this.fareRepository = fareRepository;
  }

  private Mono<Fare> value(FareQuery fareQuery) 
  {
    final Mono<Flight> flight = this.flightService.flight(fareQuery.getFlight().getId());
    
    final Mono<Set<Booking>> bookings = this.bookingService.bookingOfFlight(fareQuery.getFlight().getId());
    
    return Mono.zip(flight, bookings, (answerOne, answerTwo) -> new FareContext(answerOne, answerTwo, answerOne.getPlane(), fareQuery.getReservations()))
        .flatMap(data -> 
        {
          val reservations = fareQuery.getReservations().stream().map(element -> {
            final Optional<BigDecimal> regularPrice = data.getFlight()
                .categoryPrice(element.getSeat().categoryId());
                        
            if (regularPrice.isPresent()) 
            {
              
            	BigDecimal additionalValue = regularPrice.get();
            	if(data.getReservations().stream().anyMatch(x -> !x.getPassenger().getId().isEmpty()))
            	{
            		additionalValue = additionalValue.multiply(new BigDecimal(1.05));
            	}
              
              return Reservation.builder().passenger(element.getPassenger()).seat(element.getSeat())
                  .price(regularPrice.get().add(additionalValue)).build();
            } 
            else 
            {
              throw new IllegalArgumentException("Invalid class for discount");
            }
          }).collect(toSet());
          
          
          return Mono.just(Fare.builder().id(UUID.randomUUID().toString()).flight(data.flightInfo())
              .validUntil(LocalDateTime.now().plusMinutes(15)).reservations(reservations).build());
        });
  }

  public Mono<Fare> requestFare(FareQuery fareQuery)
  {
    return this.value(fareQuery).flatMap(this.fareRepository::create).flatMap(Mono::just);
  }

  public Mono<Fare> fare(String id)
  {
    return this.fareRepository.fare(id);
  }

}
