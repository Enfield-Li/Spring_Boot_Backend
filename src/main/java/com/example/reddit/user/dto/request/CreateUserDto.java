package com.example.reddit.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

  @NotNull(message = "Must have a username")
  @Size(min = 5, message = "Must loner than 5 characters")
  private String username;

  @NotNull(message = "Must have a password")
  @Size(min = 5, message = "Must loner than 5 characters")
  private String password;

  @Email(message = "Must be an email")
  @NotNull(message = "Must have a email")
  private String email;
}
