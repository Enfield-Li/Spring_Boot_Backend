package com.example.reddit.user.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUserDto {

  @NotNull
  @Size(min = 5)
  private String usernameOrEmail;

  @NotNull
  @Size(min = 5)
  private String password;
}
