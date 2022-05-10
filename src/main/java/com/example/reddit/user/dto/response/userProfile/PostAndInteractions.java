package com.example.reddit.user.dto.response.userProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAndInteractions {

  private Post post;
  private Interactions interactions;

  public PostAndInteractions(Post post) {
    this.post = post;
  }
}
