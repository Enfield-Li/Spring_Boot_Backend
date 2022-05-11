package com.example.reddit.mapper.dto.homePost;

import com.example.reddit.mapper.dto.Interactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAndInteractions {

  private HomePost post;
  private Interactions interactions;

  public PostAndInteractions(HomePost post) {
    this.post = post;
  }
}
