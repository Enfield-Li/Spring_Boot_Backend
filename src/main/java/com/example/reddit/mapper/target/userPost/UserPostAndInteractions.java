package com.example.reddit.mapper.target.userPost;

import com.example.reddit.mapper.target.Interactions;
import com.example.reddit.mapper.target.Post;

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
