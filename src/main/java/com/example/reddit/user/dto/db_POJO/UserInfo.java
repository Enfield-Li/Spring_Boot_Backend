package com.example.reddit.user.dto.DB_POJO;

import java.time.Instant;

public interface UserInfo {
  Long getId();
  Instant getCreatedAt();
  String getUsername();
  String getEmail();
  Integer getPostAmounts();
}
