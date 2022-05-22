package com.example.reddit.user.dto;

import com.example.reddit.post.Post;
import java.util.List;
import lombok.Data;

@Data
public class UserProfile {

  private Long id;
  private String username;
  private List<Post> posts;
}
