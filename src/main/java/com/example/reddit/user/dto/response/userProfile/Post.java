package com.example.reddit.user.dto.response.userProfile;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

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
  private Long userId;
}
