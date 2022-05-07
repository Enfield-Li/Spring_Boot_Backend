package com.example.reddit.post.dto.query;

import com.example.reddit.interactions.entity.Interactions;
import java.time.Instant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostWithInteractions {

  private Long id;
  private Instant createdAt;
  private Instant updatedAt;
  private String title;
  private String content;
  private Integer viewCount;
  private Integer votePoints;
  private Integer likePoints;
  private Integer confusedPoints;
  private Integer laughPoints;
  private Integer commentAmounts;
  private Long userId;

//   private User user;

//   private Interactions interactions;
}
