package com.flywithus.commercial.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flywithus.commercial.passenger.Address;
import com.flywithus.commercial.passenger.Contact;
import com.flywithus.commercial.passenger.PassengerDocument;

import java.util.Set;
import lombok.Data;


@Data
public class PassengerRequest {

  String name;

  @JsonProperty("last_name")
  String lastName;

  Set<PassengerDocument> documents;

  @JsonProperty("born_date")
  String bornDate;

  Address address;

  Contact contact;

}
