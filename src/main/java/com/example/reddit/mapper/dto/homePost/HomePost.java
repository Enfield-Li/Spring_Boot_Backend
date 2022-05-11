package com.example.reddit.mapper.dto.homePost;

import java.time.Instant;

import com.example.reddit.mapper.dto.userPost.AuthorInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePost {

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

  private AuthorInfo user;
}
