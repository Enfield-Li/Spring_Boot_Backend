package com.example.reddit.user.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Embeddable
public class Password {

  @Column(nullable = false)
  private String password;

  public Password() {}

  public Password(String password) {
    this.password = password;
  }

  public Password encode(String rawPassword, PasswordEncoder passwordEncoder) {
    return new Password(passwordEncoder.encode(rawPassword));
  }

  public Boolean matchPassword(
    String rawPassword,
    PasswordEncoder passwordEncoder
  ) {
    return passwordEncoder.matches(rawPassword, password);
  }
}
