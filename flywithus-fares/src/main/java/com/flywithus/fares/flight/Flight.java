package com.flywithus.fares.flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import com.flywithus.fares.ddd.Plane;
import com.flywithus.fares.ddd.RegularPrice;

import lombok.Data;



@Data
public class Flight {

  String id;

  String number;

  LocalDateTime departureAt;

  LocalDateTime arriveAt;

  Set<RegularPrice> prices;

  Plane plane;

  public Optional<BigDecimal> categoryPrice(String classId){
    return prices.stream().filter(price-> price.targetClass.getId().equalsIgnoreCase(classId))
        .map(RegularPrice::getPrice).findFirst();
  }

}
