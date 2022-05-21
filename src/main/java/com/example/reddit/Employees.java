package com.example.reddit;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employees {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  private String firstName;
  private String lastName;
  private String emailId;

  public Employees() {}

  public Employees(long id, String firstName, String lastName, String emailId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailId() {
    return this.emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public Employees id(long id) {
    setId(id);
    return this;
  }

  public Employees firstName(String firstName) {
    setFirstName(firstName);
    return this;
  }

  public Employees lastName(String lastName) {
    setLastName(lastName);
    return this;
  }

  public Employees emailId(String emailId) {
    setEmailId(emailId);
    return this;
  }

  @Override
  public String toString() {
    return (
      "{" +
      " id='" +
      getId() +
      "'" +
      ", firstName='" +
      getFirstName() +
      "'" +
      ", lastName='" +
      getLastName() +
      "'" +
      ", emailId='" +
      getEmailId() +
      "'" +
      "}"
    );
  }
}
