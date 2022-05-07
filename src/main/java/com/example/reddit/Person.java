package com.example.reddit;

import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
class Person {

  @Id
  UUID id;

  String firstName;
  String lastName;

  @Embedded
  Address address;
}
