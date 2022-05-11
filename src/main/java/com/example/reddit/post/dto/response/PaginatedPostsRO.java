package com.example.reddit.post.dto.response;

import java.util.List;

import com.example.reddit.mapper.target.homePost.PostAndInteractions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedPostsRO {

  private Boolean hasMore = true;
  private List<PostAndInteractions> postAndInteractions;
}
