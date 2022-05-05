package com.example.reddit.user.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResUser {

  private Long id;
  private String username;
  private String email;
  private Instant createdAt;
  private Integer postAmounts;
}
