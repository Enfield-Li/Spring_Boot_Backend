package com.example.reddit.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRO {

  private ResUser user;
  private ResUserError errors;

  public UserRO(ResUser user) {
    this.user = user;
  }

  public UserRO(ResUserError errors) {
    this.errors = errors;
  }
}
