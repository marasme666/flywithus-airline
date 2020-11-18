package com.flywithus.bookings.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flywithus.bookings.ddd.Seat;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;


@Data
@JsonInclude(Include.NON_NULL)
public class Reservation {

  Passenger passenger;

  Seat seat;

  BigDecimal price;

  @Builder
  public static Reservation newReservation(Passenger passenger,Seat seat,BigDecimal price){
    final Reservation reservation = new Reservation();
    reservation.passenger = passenger;
    reservation.seat = seat;
    reservation.price = price;
    return reservation;
  }

}
