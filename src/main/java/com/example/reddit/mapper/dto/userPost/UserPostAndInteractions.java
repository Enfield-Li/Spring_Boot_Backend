package com.example.reddit.mapper.dto.userPost;

import com.example.reddit.mapper.dto.Interactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostAndInteractions {

  private UserPost post;
  private Interactions interactions;

  public UserPostAndInteractions(UserPost post) {
    this.post = post;
  }
}
