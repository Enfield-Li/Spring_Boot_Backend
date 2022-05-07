package com.example.reddit.person.dto;

public interface PersonSummary {
  String getFirstName();
  String getLastName();

  AddressSummary getAddress();

  interface AddressSummary {
    String getCity();
  }
}
