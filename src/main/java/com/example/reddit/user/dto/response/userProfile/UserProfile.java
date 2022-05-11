package com.example.reddit.user.dto.response.userProfile;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

  private Long id;
  private Instant userCreatedAt;
  private String email;
  private Integer postAmounts;
  private String username;

  private Long postId;
  private Instant postCreatedAt;
  private Instant postUpdatedAt;
  private String title;
  private String content;
  private Integer viewCount;
  private Integer votePoints;
  private Integer likePoints;
  private Integer confusedPoints;
  private Integer laughPoints;
  private Integer commentAmounts;
}
