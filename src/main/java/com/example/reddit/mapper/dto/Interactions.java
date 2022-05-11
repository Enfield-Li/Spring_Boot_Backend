package com.example.reddit.mapper.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interactions {

  private Instant createdAt;
  private Instant updatedAt;
  private Boolean voteStatus;
  private Boolean likeStatus;
  private Boolean laughStatus;
  private Boolean confusedStatus;
  private Boolean read;
  private Boolean checked;
  private Long userId;
  private Long postId;
}
