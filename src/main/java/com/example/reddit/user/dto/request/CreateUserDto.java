package com.example.reddit.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

  @NotNull
  @Size(min = 5)
  private String username;

  @NotNull
  @Size(min = 5)
  private String password;

  @Email
  @NotNull
  private String email;
}
