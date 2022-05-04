package com.example.reddit.user.entity;

import javax.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  public static Password encode(String rawPassword, Class<PasswordEncoder> class1) {
    return null;
  }
}
