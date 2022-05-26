package com.example.reddit.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {

  @Size(min = 5, message = "Must loner than 5 characters")
  private String username;

  @Size(min = 5, message = "Must loner than 5 characters")
  private String password;

  @Email(message = "Must be an email")
  private String email;
}
