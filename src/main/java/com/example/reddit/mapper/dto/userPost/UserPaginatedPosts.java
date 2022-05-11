package com.example.reddit.mapper.dto.userPost;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaginatedPosts {

  private Boolean hasMore = true;
  private List<UserPostAndInteractions> postAndInteractions;
}
