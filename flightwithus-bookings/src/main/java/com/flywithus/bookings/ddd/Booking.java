package com.flywithus.bookings.ddd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flywithus.bookings.fare.Fare;
import com.flywithus.bookings.fare.Reservation;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bookings")
@JsonInclude(Include.NON_NULL)
public class Booking {

  @Id
  String id;

  Flight flight;

  Set<Seat> seats;

  Fare fare;

  Payment payment;

  public BigDecimal total(){
    return this.fare.getReservations().stream().map(Reservation::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public Long booked(){
    return Objects.isNull(seats) ? 0L : seats.size();
  }

}
