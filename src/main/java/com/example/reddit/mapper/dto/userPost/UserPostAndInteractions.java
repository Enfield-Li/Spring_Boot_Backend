package com.example.reddit.mapper.dto.userPost;

import com.example.reddit.mapper.dto.Interactions;
import com.example.reddit.mapper.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostAndInteractions {

  private Post post;
  private Interactions interactions;

  public UserPostAndInteractions(Post post) {
    this.post = post;
  }
}
