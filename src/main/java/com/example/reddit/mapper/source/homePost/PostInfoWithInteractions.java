package com.example.reddit.mapper.source.homePost;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoWithInteractions extends PostInfoWithoutInteractions {

  private Long id;
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

  private Boolean voteStatus;
  private Boolean likeStatus;
  private Boolean laughStatus;
  private Boolean confusedStatus;
}
