package com.example.reddit;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

  String zipCode;
  String city;
  String street;
}
