package com.example.reddit;

interface PersonSummary {
  String getFirstName();
  String getLastName();

  AddressSummary getAddress();

  interface AddressSummary {
    String getCity();
  }
}
