package com.example.reddit.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResUserError {

  private String field;
  private String message;

  public static ResUserError of(String field) {
    return new ResUserError(field, "Invalid " + field);
  }
}
