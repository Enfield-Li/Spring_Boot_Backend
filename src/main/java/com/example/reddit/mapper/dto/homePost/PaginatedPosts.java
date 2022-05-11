package com.example.reddit.mapper.dto.homePost;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedPosts {

  private Boolean hasMore = true;
  private List<PostAndInteractions> postAndInteractions;
}
