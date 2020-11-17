package com.flywithus.passangers.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flywithus.passangers.ddd.Passenger;


public interface PassengerRepository extends ReactiveCrudRepository<Passenger, String> {

}
