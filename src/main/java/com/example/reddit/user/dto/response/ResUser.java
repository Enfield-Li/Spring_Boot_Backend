package com.example.reddit.user.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResUser {

  private Long id;
  private String username;
  private String email;
  private Instant createdAt;
  private Integer postAmounts;

  public static ResUser of(
    Long id,
    String username,
    String email,
    Instant createdAt,
    Integer postAmounts
  ) {
    return new ResUser(id, username, email, createdAt, postAmounts);
  }
}
