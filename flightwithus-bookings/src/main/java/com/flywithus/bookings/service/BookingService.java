package com.flywithus.bookings.service;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import com.flywithus.bookings.ddd.Booking;
import com.flywithus.bookings.ddd.Payment;
import com.flywithus.bookings.ddd.Flight;
import com.flywithus.bookings.ddd.Plane;
import com.flywithus.bookings.fare.Fare;
import com.flywithus.bookings.fare.Reservation;
import com.flywithus.bookings.payment.PaymentResponse;
import com.flywithus.bookings.payment.RequestPayment;
import com.flywithus.bookings.payment.RequestPayment.FlightInfo;
import com.flywithus.bookings.repository.BookingRepository;
import com.flywithus.bookings.service.data.TotalBooked;

import com.flywithus.bookings.exception.AlreadyBookedException;
import com.flywithus.bookings.exception.SeatNotAvailableOnPlaneException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class BookingService {
 
  private final BookingRepository bookingRepository;
 
  private final FareService fareService;
 
  private final PlaneService planeService;
 
  private final PaymentRequesterService paymentRequesterService;
 
  private final FlightService flightService;
 
  public BookingService(BookingRepository bookingRepository,
      FareService fareService,
      PlaneService planeService,
      PaymentRequesterService paymentRequesterService,
      FlightService flightService) {
    this.bookingRepository = bookingRepository;
    this.fareService = fareService;
    this.planeService = planeService;
    this.paymentRequesterService = paymentRequesterService;
    this.flightService = flightService;
  }
 
  public Mono<Booking> updatePayment(PaymentResponse paymentResponse){
    final Payment payment = Payment.builder().id(paymentResponse.getId())
        .transactionId(paymentResponse.getTransactionId())
        .status(paymentResponse.getStatus()).value(paymentResponse.getValue()).build();
 
    return this.bookingRepository.findById(paymentResponse.getBookingId()).flatMap(record ->{
      record.setPayment(payment);
      return this.bookingRepository.save(record);
    });
  }
 
  public Flux<Booking> bookingsOfFlight(@NonNull String flightId){
    return bookingRepository.findByFlightId(flightId);
  }
 
  public Mono<TotalBooked> totalBooked(@NonNull String flightId){
    return Mono.just(bookingRepository.findByFlightId(flightId)
        .toStream()
          .mapToLong(Booking::booked)
          .sum())
        .flatMap(value -> Mono.just(TotalBooked.builder().total(value).build()));
  }
 
  public Mono<Booking> byId(String id){
    return this.bookingRepository.findById(id);
  }
 
  public Mono<Void> cancelBooking(String id){
    return this.bookingRepository.deleteById(id);
  }
 
  public Mono<Booking> newBooking(String fareId)
  {
         return this.fareService.fare(fareId)
                       .flatMap(fare -> Mono.zip(Mono.just(fare),this.planeService.plane(fare.getFlight().getPlane().getId()),this.bookingRepository.findByFlightId(fare.getFlight().getId()).collectList(),this.flightService.flight(fare.getFlight().getId())))
                       .flatMap(data -> {
                        final Fare fare = data.getT1();
                        final Plane plane = data.getT2();
                        final List<Booking> bookings = data.getT3();
                        final Flight flight = data.getT4();
                        fare.getReservations().forEach(reservation -> {
 
                            final boolean alreadyRegistered = bookings.stream().anyMatch(booking -> booking.getSeats().contains(reservation.getSeat()));
 
                            final boolean seatAvailableOnPlane = plane.exist(reservation.getSeat());
                            if(!seatAvailableOnPlane)
                            {
                              throw SeatNotAvailableOnPlaneException.builder().flight(fare.getFlight()).plane(plane).seat(reservation.getSeat()).build();
                            }
 
                            if(alreadyRegistered)
                            {
                              throw AlreadyBookedException.builder().flight(fare.getFlight()).seat(reservation.getSeat()).build();
                            }
                          });
 
                          return Mono.just(Booking.builder().seats(fare.getReservations().stream().map(Reservation::getSeat).collect(toSet())).flight(flight).fare(fare).build());                        
                       })
                       .flatMap(this.bookingRepository::save)
                       .flatMap(booking -> { 
                             
                             final RequestPayment requestPayment = RequestPayment.builder().bookingId(booking.getId())
                                   .createdAt(LocalDateTime.now().toString()).value(booking.total())
                                   .flightInfo(
                                       FlightInfo.builder().id(booking.getFlight().getId())
                                           .number(booking.getFlight().getNumber()).build()).build();
                             return this.paymentRequesterService.requestPayment(requestPayment);
                       })
                       .flatMap(requestPayment -> this.bookingRepository.findById(requestPayment.getBookingId()));
                       
  }
 
}
