package com.example.reddit.mapper.dto.homePost;

import com.example.reddit.mapper.dto.Interactions;
import com.example.reddit.mapper.dto.Post;

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
