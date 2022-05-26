package com.example.reddit.user.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {

  @NotNull(message = "Must be a username or an email")
  @Size(min = 5, message = "Must loner than 5 characters")
  private String usernameOrEmail;

  @NotNull(message = "Must have a password")
  @Size(min = 5, message = "Must loner than 5 characters")
  private String password;
}
