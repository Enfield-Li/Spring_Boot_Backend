package com.example.reddit.mapper.DTO_POJO;

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
