package com.example.reddit.mapper.source.userPost;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostInfoWithInteractions extends UserPostInfoWitoutInteractions {

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

  private Instant interactionCreatedAt;
  private Instant interactionUpdatedAt;
  private Boolean voteStatus;
  private Boolean likeStatus;
  private Boolean laughStatus;
  private Boolean confusedStatus;
  private Boolean read;
  private Boolean checked;
}
