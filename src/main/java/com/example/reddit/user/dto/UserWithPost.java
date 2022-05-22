package com.example.reddit.user.dto;

import com.example.reddit.post.Post;
import lombok.Data;

@Data
public class UserWithPost {

  private Long id;
  private String username;
  private Long postId;
  private String title;
  private String content;
}
