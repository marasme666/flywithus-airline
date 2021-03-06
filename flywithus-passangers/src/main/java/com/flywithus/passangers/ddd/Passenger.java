package com.flywithus.passangers.ddd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.flywithus.passangers.resource.data.PassengerRequest;


@Data
@Document(collection = "passengers")
@NoArgsConstructor
public class Passenger {

  private static final transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @Id
  String id;

  String name;

  String lastName;

  Set<PassengerDocument> documents;

  Address address;

  LocalDate bornDate;

  Contact contact;

  @Builder
  public static Passenger newPassenger(String name,String lastName,Set<PassengerDocument> documents,Address address,String bornDate,Contact contact){
    final Passenger passenger = new Passenger();
    passenger.setName(name);
    passenger.setLastName(lastName);
    passenger.setContact(contact);
    passenger.setAddress(address);
    passenger.setDocuments(documents);
    passenger.setBornDate(LocalDate.parse(bornDate,FORMATTER));
    return passenger;
  }

  public Passenger fromPassengerRequest(PassengerRequest passengerRequest){
    this.name = passengerRequest.getName();
    this.lastName = passengerRequest.getLastName();
    this.contact  = passengerRequest.getContact();
    this.address = passengerRequest.getAddress();
    this.documents = passengerRequest.getDocuments();
    this.bornDate = LocalDate.parse(passengerRequest.getBornDate(),FORMATTER);
    return this;
  }

}
