package com.example.reddit.post.dto.interfaces;

import java.time.Instant;

public interface PostWithAuthorAndInteractions {
  Long getId();

  Instant getCreatedAt();

  Instant getUpdatedAt();

  String getTitle();

  String getContent();

  Long getUserId();

  UserInfo getUser();

  interface UserInfo {
    String getUsername();
  }
}
