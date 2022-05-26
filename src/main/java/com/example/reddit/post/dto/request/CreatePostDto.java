package com.example.reddit.post.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostDto {

  @NotNull(message = "Must have a title")
  @Size(min = 10, message = "Must loner than 10 characters")
  String title;

  String content;
}
