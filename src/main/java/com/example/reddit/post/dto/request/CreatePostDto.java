package com.example.reddit.post.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostDto {

  @Min(10)
  @NotNull
  String title;

  String content;
}
