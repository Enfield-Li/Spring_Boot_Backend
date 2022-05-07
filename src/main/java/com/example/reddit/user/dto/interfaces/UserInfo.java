package com.example.reddit.user.dto.interfaces;

import java.time.Instant;

public interface UserInfo {
  Long getId();
  Instant getCreatedAt();
  String getUsername();
  String getEmail();
  Integer getPostAmounts();
}
