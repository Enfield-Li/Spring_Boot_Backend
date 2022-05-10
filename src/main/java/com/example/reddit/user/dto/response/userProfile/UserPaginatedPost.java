package com.example.reddit.user.dto.response.userProfile;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaginatedPost {

  private Boolean hasMore = true;
  private List<PostAndInteractions> postAndInteractions;
}
