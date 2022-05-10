package com.example.reddit.user.dto.db;

import lombok.Data;

@Data
public class UserAtZero {

  private Long userId;
  private String username;
  private Long postId;
  private String postTitle;
  private String postContent;
}
