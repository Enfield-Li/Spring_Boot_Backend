package com.example.reddit.comments.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateCommentOrReplyDto {

  @NotNull(message = "Must have a comment_text")
  private String comment_text;

  private Integer parentCommentId;
  private Integer parentCommentUserId;
}
