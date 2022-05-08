package com.example.reddit.user.entity;

import java.util.Objects;

public class UserPartialInfo {

  private Long id;
  private String username;

  public UserPartialInfo() {}

  public UserPartialInfo(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserPartialInfo id(Long id) {
    setId(id);
    return this;
  }

  public UserPartialInfo username(String username) {
    setUsername(username);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof UserPartialInfo)) {
      return false;
    }
    UserPartialInfo userPartialInfo = (UserPartialInfo) o;
    return (
      Objects.equals(id, userPartialInfo.id) &&
      Objects.equals(username, userPartialInfo.username)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }

  @Override
  public String toString() {
    return (
      "{" + " id='" + getId() + "'" + ", username='" + getUsername() + "'" + "}"
    );
  }
}
