package com.example.reddit.user.dto.response.userProfile;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  private Long id;
  private Instant userCreatedAt;
  private String email;
  private Integer postAmounts;
  private String username;
}
