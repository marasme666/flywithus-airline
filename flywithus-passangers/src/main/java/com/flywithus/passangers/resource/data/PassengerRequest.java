package com.flywithus.passangers.resource.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flywithus.passangers.ddd.Address;
import com.flywithus.passangers.ddd.Contact;
import com.flywithus.passangers.ddd.PassengerDocument;

import java.util.Set;
import lombok.Data;


@Data
public class PassengerRequest 
{

  String name;

  @JsonProperty("last_name")
  String lastName;

  Set<PassengerDocument> documents;

  @JsonProperty("born_date")
  String bornDate;

  Address address;

  Contact contact;

}
