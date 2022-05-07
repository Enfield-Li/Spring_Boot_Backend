package com.example.reddit.person;

import com.example.reddit.person.dto.Address;
import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Person {

  @Id
  UUID id;

  String firstName;
  String lastName;

  @Embedded
  Address address;
}
