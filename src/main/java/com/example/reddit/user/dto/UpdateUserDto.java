package com.example.reddit.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {

  @Size(min = 5)
  private String username;

  @Size(min = 5)
  private String password;

  @Email
  private String email;
}
