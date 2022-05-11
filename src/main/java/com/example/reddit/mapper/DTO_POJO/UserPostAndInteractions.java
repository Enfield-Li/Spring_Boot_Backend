package com.example.reddit.mapper.DTO_POJO;

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
