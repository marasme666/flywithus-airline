package com.flywithus.bookings.exception;

import com.flywithus.bookings.ddd.Flight;
import com.flywithus.bookings.ddd.Seat;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;


@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class AlreadyBookedException extends RuntimeException {

  Flight flight;

  Seat seat;

}
