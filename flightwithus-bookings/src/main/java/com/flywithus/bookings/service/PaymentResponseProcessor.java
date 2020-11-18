package com.flywithus.bookings.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.flywithus.bookings.payment.PaymentResponse;


@Service
@Slf4j
public class PaymentResponseProcessor {
 
  private final BookingService bookingService;
 
  public PaymentResponseProcessor(BookingService bookingService) {
    this.bookingService = bookingService;
  }
 
  @RabbitListener(id = "payment-response-processor-listener",queues = "${amqp.payments.queue.response}")
  public void processPaymentResponse(PaymentResponse paymentResponse){
    this.bookingService.updatePayment(paymentResponse).subscribe(booking ->{
      log.info("Payment response received for booking: " + booking.getId() );
    });
  }
 
}
