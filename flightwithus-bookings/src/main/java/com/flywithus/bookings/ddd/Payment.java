package com.flywithus.bookings.ddd;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import com.flywithus.bookings.payment.PaymentStatus;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Field("payment_id")
  String id;

  String transactionId;

  BigDecimal value;

  PaymentStatus status;

}
