package com.flywithus.bookings.payment;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPayment {

  String bookingId;

  FlightInfo flightInfo;

  String createdAt;

  BigDecimal value;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FlightInfo{

    String id;

    String number;

  }

}
