package com.example.reddit.person.dto;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

  String zipCode;
  String city;
  String street;
}
